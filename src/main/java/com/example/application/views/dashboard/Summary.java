package com.example.application.views.dashboard;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import java.util.Map;

public class Summary extends HorizontalLayout {
    public Summary(Map<String, Double> summaries) {
        addClassName("sumup-tile");

        summaries.entrySet().stream()
                .map(this::createBlock)
                .forEach(this::add);
    }

    private Div createBlock(Map.Entry<String, Double> e) {
        Div block = new Div();
        block.add(new H2(e.getKey()));
        block.add(new H3(e.getValue() + " hours"));
        return block;
    }
}
