package pu.project.app.views.search;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

import pu.project.app.models.Book;
import pu.project.app.repo.BookRepo;
import pu.project.app.services.BookService;
import pu.project.app.views.MainLayout;

@PageTitle("Търси")
@Route(value = "search", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)

public class SearchView extends VerticalLayout {

    private final BookService bookService;
    private final BookRepo bookRepo;

    private final Grid<Book> grid = new Grid<>(Book.class);
    private final TextField titleFilter = new TextField();
    private final TextField authorFilter = new TextField();

    @Autowired
    public SearchView(BookService bookService, BookRepo bookRepo) {
        this.bookService = bookService;
        this.bookRepo = bookRepo;

        configureGrid();
        configureFilters();

        HorizontalLayout filtersLayout = new HorizontalLayout(titleFilter, authorFilter);
        
        VerticalLayout contentLayout = new VerticalLayout(filtersLayout, grid);
        contentLayout.setSizeFull();
        contentLayout.setFlexGrow(1, grid);

        add(contentLayout);
        setSizeFull();
    }

    private void configureGrid() {
        grid.removeAllColumns();

        grid.addColumn(Book::getIsbn).setHeader("ISBN");
        grid.addColumn(Book::getTitle).setHeader("Заглавие");
        grid.addColumn(Book::getPublishingYear).setHeader("Година");
        grid.addColumn(b->b.getAuthor().getName()).setHeader("Автор");
        grid.addColumn(p->p.getPublisher().getName()).setHeader("Издател");
    
        updateGrid();
    }

    private void configureFilters() {
        titleFilter.setValueChangeMode(ValueChangeMode.EAGER);
        titleFilter.setPlaceholder("Въведи заглавие");
        titleFilter.addValueChangeListener(e -> updateGrid());

        authorFilter.setValueChangeMode(ValueChangeMode.EAGER);
        authorFilter.setPlaceholder("Въведи автор");
        authorFilter.addValueChangeListener(e -> updateGrid());
    }

    private void updateGrid() {
        String title = titleFilter.getValue();
        String author = authorFilter.getValue();
    
        List<Book> books = bookRepo.findByTitleAndAuthor(title.toLowerCase(), author.toLowerCase(), PageRequest.of(0, 10)).getContent();
    
        if (books != null) {
            grid.setItems(books);
        } else {
        }
    }
}