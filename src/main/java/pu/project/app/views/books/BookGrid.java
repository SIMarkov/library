package pu.project.app.views.books;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;

import pu.project.app.models.Book;

public class BookGrid extends Grid<Book> {
    public BookGrid(){
        super(Book.class, false);
        initGrid();
    }

    private void initGrid() {
        setSizeFull();
        addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        addColumn(Book::getIsbn).setHeader("ISBN").setSortable(true);
        addColumn(Book::getTitle).setFrozen(true).setHeader("Заглавие").setSortable(true);
        addColumn(Book::getPublishingYear).setHeader("Година").setSortable(true);
        addColumn(b->b.getAuthor().getName()).setHeader("Автор").setSortable(true);
        addColumn(p->p.getPublisher().getName()).setHeader("Издател").setSortable(true);
    }
}