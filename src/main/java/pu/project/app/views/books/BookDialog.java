package pu.project.app.views.books;

import org.springframework.data.domain.PageRequest;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import pu.project.app.models.Author;
import pu.project.app.models.Book;
import pu.project.app.models.Publisher;
import pu.project.app.services.AuthorService;
import pu.project.app.services.BookService;
import pu.project.app.services.PublisherService;

public class BookDialog extends Dialog {

    private final Book book;
    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private TextField title = new TextField("Заглавие");
    private TextField isbn = new TextField("ISBN");
    private IntegerField publishingYear = new IntegerField("Година");
    private ComboBox<Author> author = new ComboBox<>("Автор");
    private ComboBox<Publisher> publisher = new ComboBox<>("Издател");
    private BeanValidationBinder<Book> binder = new BeanValidationBinder<>(Book.class);
    private boolean isEditMode;
    private Button saveButton;

    public BookDialog(Book book, BookService bookService, AuthorService authorService, PublisherService publisherService) {
        this.book = book;
        this.bookService = bookService;
        this.authorService = authorService;
        this.publisherService = publisherService;
        isEditMode = book.getId() != null;
        initContent();
    }

    private void initContent() {
        setHeaderTitle(isEditMode ? "Промяна на книга" : "Създаване на книга");
        createMainContent();
        configureFooter();
    }

    private void configureFooter() {
        saveButton = new Button("Запази", l -> {
            BinderValidationStatus<Book> validated = binder.validate();
            if (validated.isOk()) {
                try {
                    binder.writeBean(book);
                    if (isEditMode) {
                        bookService.update(book);
                    } else {
                        bookService.create(book);
                    }
                    close();
                } catch (ValidationException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        final Button cancelButton = new Button("Откажи", l -> close());
        getFooter().add(cancelButton, saveButton);
    }

    private void createMainContent() {
        author.setItems(query -> authorService.list(
                PageRequest.of(query.getPage(),
                        query.getPageSize(),
                        VaadinSpringDataHelpers.toSpringDataSort(query))));
        author.setItemLabelGenerator(Author::getName);
        publisher.setItems(query -> publisherService.list(
                PageRequest.of(query.getPage(),
                        query.getPageSize(),
                        VaadinSpringDataHelpers.toSpringDataSort(query))));
        publisher.setItemLabelGenerator(Publisher::getName);
        binder.bindInstanceFields(this);
        binder.forField(isbn).asRequired("Въведете ISBN!").bind(Book::getIsbn, Book::setIsbn)
                .setAsRequiredEnabled(true);
        binder.forField(title).asRequired("Въведете заглавие!").bind(Book::getTitle, Book::setTitle)
                .setAsRequiredEnabled(true);
        binder.forField(publishingYear).asRequired("Въведете година!").bind(Book::getPublishingYear, Book::setPublishingYear)
                .setAsRequiredEnabled(true);
        binder.forField(author).asRequired("Въведете автор!").bind(Book::getAuthor, Book::setAuthor)
                .setAsRequiredEnabled(true);
        binder.forField(publisher).asRequired("Въведете издател!").bind(Book::getPublisher, Book::setPublisher)
                .setAsRequiredEnabled(true);
        binder.readBean(book);
        FormLayout formLayout = new FormLayout(isbn, title, publishingYear, author, publisher);
        formLayout.setSizeFull();
        add(formLayout);
    }

    public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        saveButton.addClickListener(listener);
    }
}