package com.example.application.views.tracker;

import com.example.application.data.entity.TimeSlot;
import com.example.application.data.service.TrackerService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.PermitAll;
import java.util.List;

@PermitAll
@Route(value = "tracker", layout = MainLayout.class)
@PageTitle("Tracker | Vaadin CRM")
public class TimeTrackerView extends VerticalLayout {
    private static final String DESCRIPTION_FIELD_NAME = "description";
    ValidationMessage descriptionValidationMessage = new ValidationMessage();
    Grid<TimeSlot> grid = new Grid<>(TimeSlot.class);

    Binder<TimeSlot> binder = new BeanValidationBinder<>(TimeSlot.class);
    Editor<TimeSlot> editor = grid.getEditor();
    private TrackerService service;

    public TimeTrackerView(TrackerService service) {
        this.service = service;
        addClassName("tracker-view");

        setSizeFull();
        configureGrid();
        configureEditor();

        add(descriptionValidationMessage, getContent());

        updateList();
    }

    private void configureGrid() {
        grid.addClassNames("slots-grid");
        grid.setSizeFull();
        grid.setColumns(DESCRIPTION_FIELD_NAME);
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
        List<TimeSlot> allSlots = service.findAllSlots();
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

        editor.addCancelListener(e -> descriptionValidationMessage.setText(""));
        editor.addCloseListener(e -> {
            try {
                binder.writeBean(e.getItem());
            } catch (ValidationException ex) {
                 return;
            }
            service.save(e.getItem());
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
