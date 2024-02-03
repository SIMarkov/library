package pu.project.app.views.author;

import com.vaadin.flow.component.grid.Grid;

import pu.project.app.models.Author;

public class AuthorGrid extends Grid<Author> {
    public AuthorGrid(){
        super(Author.class, false);
        configureView();
    }

    private void configureView() {
        addColumn(Author::getName).setHeader("Име").setFrozen(true).setSortable(true);
        addColumn(Author::getBirthYear).setHeader("Роден").setSortable(true);
    }
}