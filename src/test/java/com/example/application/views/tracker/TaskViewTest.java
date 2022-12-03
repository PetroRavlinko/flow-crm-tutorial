package com.example.application.views.tracker;

import com.example.application.data.entity.Task;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.data.provider.ListDataProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
class TaskViewTest {
    @Autowired
    private TaskView taskView;

    @Test
    void formShownWhenContactSelected() {
        Grid<Task> grid = taskView.grid;
        Task task = getFirstItem(grid);

        Editor<Task> editor = taskView.editor;

        assertFalse(editor.isOpen());
        grid.asSingleSelect().setValue(task);
    }

    private Task getFirstItem(Grid<Task> grid) {
        return ((ListDataProvider<Task>) grid.getDataProvider()).getItems().iterator().next();
    }
}