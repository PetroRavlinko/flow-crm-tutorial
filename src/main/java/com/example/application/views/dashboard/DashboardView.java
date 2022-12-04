package com.example.application.views.dashboard;

import com.example.application.data.service.TimeSlotService;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;

import javax.annotation.security.PermitAll;
import java.util.Map;
import java.util.Optional;

@org.springframework.stereotype.Component
@Scope("prototype")
@PermitAll
@Route(value = "", layout = MainLayout.class)
@PageTitle("Dashboard | Time Tracker")
public class DashboardView extends VerticalLayout {
    Summary summary;

    public DashboardView(TimeSlotService service) {
        addClassName("dashboard-view");

        summary = new Summary(Map.of(
                "Total", Optional.ofNullable(service.totalHours()).orElse(.0),
                "Unassigned", Optional.ofNullable(service.totalUnassignedHours()).orElse(.0)
        ));

        add(summary);
    }
}
