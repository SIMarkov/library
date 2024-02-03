package pu.project.app.views.books;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.selection.SingleSelect;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

import pu.project.app.models.Book;
import pu.project.app.services.AuthorService;
import pu.project.app.services.BookService;
import pu.project.app.services.PublisherService;
import pu.project.app.views.MainLayout;

@PageTitle("Книги")
@Route(value = "books", layout = MainLayout.class)
@AnonymousAllowed
public class BookView extends VerticalLayout {
    @Autowired
    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private BookGrid bookGrid;
    private Button editButton;
    private Button deleteButton;

    public BookView(BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        initContent();
    }

    private void initContent() {
        setSizeFull();
        add(createButtons(), createGrid());
    }

    private Component createGrid() {
        bookGrid = new BookGrid();
        bookGrid.setSizeFull();
        reloadGrid();
        SingleSelect<Grid<Book>, Book> singleSelect = bookGrid.asSingleSelect();
        singleSelect.addValueChangeListener(event -> {
            Book selectedBook = event.getValue();
            editButton.setEnabled(selectedBook != null);
            deleteButton.setEnabled(selectedBook != null);
        });
        return bookGrid;
    }

    private HorizontalLayout createButtons() {
        final Button addButton = new Button(new Icon(VaadinIcon.PENCIL));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.setTooltipText("Добави книга");
        addButton.addClickListener(l -> openBookDialog(new Book()));
        editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton.addClickListener(l -> openBookDialog(bookGrid.asSingleSelect().getValue()));
        editButton.setEnabled(false);
        editButton.setTooltipText("Промени книга");
        deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.setEnabled(false);
        deleteButton.setTooltipText("Изтрий книга");
        deleteButton.addClickListener(event -> {
            Dialog dialog = new Dialog();
            Book selectedBook = bookGrid.asSingleSelect().getValue();
            if (selectedBook != null) {
                dialog.setHeaderTitle("Сигурни ли сте, че искате да изтриете книга: " + selectedBook.getTitle());
                Button ok = new Button("Да", ll -> {
                    bookService.remove(selectedBook.getId());
                    reloadGrid();
                    dialog.close();
                });
                ok.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
                Button no = new Button("Не", ll -> dialog.close());
                dialog.getFooter().add(no, ok);
                dialog.open();
            }
        });
        return new HorizontalLayout(addButton, editButton, deleteButton);
    }

    private void openBookDialog(final Book book) {
        BookDialog bookDialog = new BookDialog(book, bookService, authorService, publisherService);
        bookDialog.addSaveClickListener(event -> reloadGrid());
        bookDialog.open();
    }

    private void reloadGrid() {
        bookGrid.setItems(bookService.findAll());
    }
}