package com.example.application.views.tracker;

import com.example.application.data.entity.Task;
import com.example.application.data.entity.TimeSlot;
import com.example.application.data.service.TaskService;
import com.example.application.data.service.TimeSlotService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.List;
import java.util.Objects;

@PermitAll
@Route(value = "tracker", layout = MainLayout.class)
@PageTitle("Time | Time Tracker")
public class TimeSlotTrackerView extends VerticalLayout {
    private static final String DESCRIPTION_FIELD_NAME = "description";
    private static final String TASK_FIELD_NAME = "task";
    public static final String HOURS_FIELD_NAME = "hours";
    ValidationMessage descriptionValidationMessage = new ValidationMessage();
    ValidationMessage hoursValidationMessage = new ValidationMessage();
    Grid<TimeSlot> grid = new Grid<>(TimeSlot.class);

    Binder<TimeSlot> binder = new BeanValidationBinder<>(TimeSlot.class);
    Editor<TimeSlot> editor = grid.getEditor();
    private TimeSlotService timeSlotService;
    private TaskService taskService;

    public TimeSlotTrackerView(TimeSlotService timeSlotService, TaskService taskService) {
        this.timeSlotService = timeSlotService;
        this.taskService = taskService;
        addClassName("slots-view");

        setSizeFull();
        configureGrid();
        configureEditor();

        add(descriptionValidationMessage, hoursValidationMessage, getContent());

        updateList();
    }

    private void configureGrid() {
        grid.addClassNames("slots-grid");
        grid.setSizeFull();
        grid.setColumns(DESCRIPTION_FIELD_NAME, HOURS_FIELD_NAME);
        grid.addColumn(timeSlot -> Objects.nonNull(timeSlot.getTask()) ? timeSlot.getTask().getName() : "").setKey(TASK_FIELD_NAME).setHeader("Task");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.addItemDoubleClickListener(e -> {
            editor.editItem(e.getItem());
            Component editorComponent = e.getColumn().getEditorComponent();
            if (editorComponent instanceof Focusable) {
                ((Focusable) editorComponent).focus();
            }
        });
    }

    private Component getContent() {
        HorizontalLayout content = new HorizontalLayout(grid);
        content.setFlexGrow(2, grid);
        content.addClassNames("content");
        content.setSizeFull();
        return content;
    }

    private void updateList() {
        List<TimeSlot> allSlots = timeSlotService.findAllSlots();
        allSlots.add(new TimeSlot());
        grid.setItems(allSlots);
    }

    private void configureEditor() {
        editor.setBinder(binder);

        TextField descriptionField = new TextField();
        descriptionField.setWidthFull();
        addCloseHandler(descriptionField, editor);
        addSaveHandler(descriptionField, editor);
        binder.forField(descriptionField)
                .asRequired("Description must not be empty")
                .withStatusLabel(descriptionValidationMessage)
                .bind(TimeSlot::getDescription, TimeSlot::setDescription);
        grid.getColumns().stream()
                .filter(c -> DESCRIPTION_FIELD_NAME.equals(c.getKey()))
                .findAny()
                .orElseThrow()
                .setEditorComponent(descriptionField);

        NumberField hoursField = new NumberField();
        hoursField.setWidthFull();
        addCloseHandler(hoursField, editor);
        addSaveHandler(hoursField, editor);
        binder.forField(hoursField)
                .withValidator(h -> Objects.nonNull(h) && h > 0, "Hours should be positive")
                .withStatusLabel(hoursValidationMessage)
                .bind(TimeSlot::getHours, TimeSlot::setHours);
        grid.getColumns().stream()
                .filter(c -> HOURS_FIELD_NAME.equals(c.getKey()))
                .findAny()
                .orElseThrow()
                .setEditorComponent(hoursField);


        ComboBox<Task> taskComboBox= new ComboBox();
        taskComboBox.setWidthFull();
        taskComboBox.setItems(taskService.findAll());
        taskComboBox.setItemLabelGenerator(Task::getName);
        addCloseHandler(taskComboBox, editor);
        addSaveHandler(taskComboBox, editor);
        binder.forField(taskComboBox)
                .withStatusLabel(hoursValidationMessage)
                .bind(TimeSlot::getTask, TimeSlot::setTask);
        grid.getColumns().stream()
                .filter(c -> TASK_FIELD_NAME.equals(c.getKey()))
                .findAny()
                .orElseThrow()
                .setEditorComponent(taskComboBox);

        editor.addCancelListener(e -> {
            descriptionValidationMessage.setText("");
            hoursValidationMessage.setText("");
        });
        editor.addCloseListener(e -> {
            try {
                binder.writeBean(e.getItem());
            } catch (ValidationException ex) {
                return;
            }
            timeSlotService.save(e.getItem());
            updateList();
        });
    }

    private static void addCloseHandler(Component textField, Editor<TimeSlot> editor) {
        textField.getElement().addEventListener("keydown", e -> editor.cancel())
                .setFilter("event.code === 'Escape'");
    }

    private static void addSaveHandler(Component textField, Editor<TimeSlot> editor) {
        textField.getElement().addEventListener("keydown", e -> editor.save())
                .setFilter("event.code === 'Enter'");
    }
}
