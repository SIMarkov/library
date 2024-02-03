package pu.project.app.views.publisher;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;

import pu.project.app.models.Publisher;
import pu.project.app.services.PublisherService;

public class PublisherDialog extends Dialog {

    private final Publisher publisher;
    private final PublisherService publisherService;
    private Button saveButton;
    private Binder<Publisher> binder;

    public PublisherDialog(Publisher publisher, PublisherService publisherService) {
        this.publisher = publisher;
        this.publisherService = publisherService;
        initContent();
    }

    private void initContent() {
        setHeaderTitle(publisher.getId() != null ? "Промени издател" : "Добави издател");
        configureContent();
        configureFooter();
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
    }

    private void configureContent() {
        TextField name = new TextField("Име");
        name.setRequired(true);
        TextField city = new TextField("Град");
        city.setRequired(true);

        binder = new Binder<>(Publisher.class);
        binder.forField(name).asRequired("Въведете име!").bind(Publisher::getName, Publisher::setName)
                        .setAsRequiredEnabled(true);;
        binder.forField(city).asRequired("Въведете град!").bind(Publisher::getCity, Publisher::setCity)
                        .setAsRequiredEnabled(true);
        binder.readBean(publisher);
        FormLayout formLayout = new FormLayout(name, city);
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
                binder.writeBean(publisher);
                publisherService.create(publisher);
                close();
            } catch (ValidationException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public void addSaveClickListener(ComponentEventListener<ClickEvent<Button>> listener) {
        saveButton.addClickListener(listener);
    }
}