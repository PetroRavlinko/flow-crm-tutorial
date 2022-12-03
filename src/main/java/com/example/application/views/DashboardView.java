package com.example.application.views;

import com.example.application.data.service.TaskService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;

@org.springframework.stereotype.Component
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Dashboard | Time Tracker")
public class DashboardView extends VerticalLayout {
    public DashboardView(TaskService service) {
        addClassName("dashboard-view");
    }
}
