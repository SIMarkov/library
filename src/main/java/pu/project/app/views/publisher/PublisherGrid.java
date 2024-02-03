package pu.project.app.views.publisher;

import com.vaadin.flow.component.grid.Grid;

import pu.project.app.models.Publisher;

public class PublisherGrid extends Grid<Publisher> {
    public PublisherGrid() {
        super(Publisher.class, false);
        configureView();
    }

    private void configureView() {
        addColumn("name").setHeader("Име").setFrozen(true);
        addColumn(Publisher::getCity).setHeader("Град").setSortable(true);
    }
}