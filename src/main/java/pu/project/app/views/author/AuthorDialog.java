package pu.project.app.views.author;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import pu.project.app.models.Author;
import pu.project.app.services.AuthorService;

public class AuthorDialog extends Dialog {

    private final Author author;
    private final AuthorService authorService;
    private Button saveButton;
    private Binder<Author> binder;

    public AuthorDialog(Author author, AuthorService authorService) {
        this.author = author;
        this.authorService = authorService;
        initContent();
    }

    private void initContent() {
        setHeaderTitle(author.getId() != null ? "Промени автор" : "Създай автор");
        configureContent();
        configureFooter();
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
    }

    private void configureContent() {
        TextField name = new TextField("Име");
        name.setRequired(true);
        IntegerField birthYear = new IntegerField("Роден");
        birthYear.setRequired(true);
        binder = new Binder<>(Author.class);
        binder.forField(name).asRequired("Въведете име!").bind(Author::getName, Author::setName)
                .setAsRequiredEnabled(true);
        binder.forField(birthYear).asRequired("Въведете година на раждане!").bind(Author::getBirthYear, Author::setBirthYear)
                .setAsRequiredEnabled(true);
        binder.readBean(author);
        FormLayout formLayout = new FormLayout(name, birthYear);
        add(formLayout);
    }

    private void configureFooter() {
        saveButton = new Button("Съхрани");
        saveButton.addClickListener(save());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Откажи", e -> close());
        getFooter().add(cancelButton);
        getFooter().add(saveButton);
    }

    private ComponentEventListener<ClickEvent<Button>> save() {
        return ll -> {
            try {
                binder.writeBean(author);
                authorService.create(author);
                close();
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener){
        saveButton.addClickListener(listener);
    }
}