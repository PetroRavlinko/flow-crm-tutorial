package com.example.application.views.tracker;

import com.example.application.data.entity.Task;
import com.example.application.data.service.TaskService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
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
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.List;

@org.springframework.stereotype.Component
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Task | Vaadin CRM")
public class TaskView extends VerticalLayout {
    private static final String NAME_FIELD_NAME = "name";
    ValidationMessage nameValidationMessage = new ValidationMessage();
    Grid<Task> grid = new Grid<>(Task.class);

    Binder<Task> binder = new BeanValidationBinder<>(Task.class);
    Editor<Task> editor = grid.getEditor();
    private TaskService service;

    public TaskView(TaskService service) {
        this.service = service;
        addClassName("tracker-view");

        setSizeFull();
        configureGrid();
        configureEditor();

        add(nameValidationMessage, getContent());

        updateList();
    }

    private void configureGrid() {
        grid.addClassNames("slots-grid");
        grid.setSizeFull();
        grid.setColumns(NAME_FIELD_NAME);
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
        List<Task> all = service.findAll();
        all.add(new Task());
        grid.setItems(all);
    }

    private void configureEditor() {
        editor.setBinder(binder);

        TextField descriptionField = new TextField();
        descriptionField.setWidthFull();
        addCloseHandler(descriptionField, editor);
        addSaveHandler(descriptionField, editor);
        binder.forField(descriptionField)
                .asRequired("Name must not be empty")
                .withStatusLabel(nameValidationMessage)
                .bind(Task::getName, Task::setName);
        grid.getColumns().stream()
                .filter(c -> NAME_FIELD_NAME.equals(c.getKey()))
                .findAny()
                .orElseThrow()
                .setEditorComponent(descriptionField);

        editor.addCancelListener(e -> {
            nameValidationMessage.setText("");
        });
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

    private static void addCloseHandler(Component textField, Editor<Task> editor) {
        textField.getElement().addEventListener("keydown", e -> editor.cancel())
                .setFilter("event.code === 'Escape'");
    }

    private static void addSaveHandler(Component textField, Editor<Task> editor) {
        textField.getElement().addEventListener("keydown", e -> editor.save())
                .setFilter("event.code === 'Enter'");
    }
}
