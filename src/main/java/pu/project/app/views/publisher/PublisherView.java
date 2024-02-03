package pu.project.app.views.publisher;

import com.vaadin.flow.component.accordion.Accordion;
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

import pu.project.app.models.Publisher;
import pu.project.app.services.PublisherService;
import pu.project.app.views.MainLayout;

@PageTitle("Издатели")
@Route(value = "publisher", layout = MainLayout.class)
@AnonymousAllowed
public class PublisherView extends VerticalLayout {
    private PublisherService publisherService;
    private Grid<Publisher> publisherGrid;
    private Button editPublisherButton;
    private Button removePublisherButton;

    public PublisherView(final PublisherService publisherService) {
        this.publisherService = publisherService;
        initContent();
    }

    private void initContent() {
        HorizontalLayout buttons = new HorizontalLayout(createAddButton(), createEditButton(), createDeleteButton());
        add(buttons);
        add(createPublisherGrid());
        Accordion accordion = new Accordion();
        accordion.setSizeFull();
    }

    private Grid<Publisher> createPublisherGrid() {
        publisherGrid = new PublisherGrid();
        publisherGrid.setMinHeight("300px");
        publisherGrid.addItemDoubleClickListener(l -> openPublisherGrid(l.getItem()));
        SingleSelect<Grid<Publisher>, Publisher> singleSelect = publisherGrid.asSingleSelect();
        singleSelect.addValueChangeListener(l -> {
            Publisher value = l.getValue();
            boolean enabled = value != null;
            editPublisherButton.setEnabled(enabled);
            removePublisherButton.setEnabled(enabled);
        });
        reloadGrid();
        return publisherGrid;
    }

    private Button createDeleteButton() {
        removePublisherButton = new Button(new Icon(VaadinIcon.TRASH));
        removePublisherButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        removePublisherButton.setEnabled(false);
        removePublisherButton.setTooltipText("Изтрий издател");
        removePublisherButton.addClickListener(l -> {
            Dialog dialog = new Dialog();
            Publisher value = publisherGrid.asSingleSelect().getValue();
            dialog.setHeaderTitle("Сигурни ли сте, че искате да изтриете издател: " + value.getName());
            Button ok = new Button("Да", ll -> {
                publisherService.remove(value.getId());
                reloadGrid();
                dialog.close();
            });
            ok.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            Button no = new Button("Не", ll -> dialog.close());
                dialog.getFooter().add(no, ok);
                dialog.open();
        });
        return removePublisherButton;
    }

    private Button createEditButton() {
        editPublisherButton = new Button(new Icon(VaadinIcon.EDIT));
        editPublisherButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editPublisherButton.addClickListener(l -> openPublisherGrid(publisherGrid.asSingleSelect().getValue()));
        editPublisherButton.setEnabled(false);
        editPublisherButton.setTooltipText("Промени издател");
        return editPublisherButton;
    }

    private void openPublisherGrid(Publisher publisher) {
        PublisherDialog dialog = new PublisherDialog(publisher, publisherService);
        dialog.addSaveClickListener(ll -> {
            reloadGrid();
            dialog.close();
        });
        dialog.open();
    }

    private Button createAddButton() {
        Button addPublisherButton = new Button(new Icon(VaadinIcon.PENCIL));
        addPublisherButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addPublisherButton.setTooltipText("Добави издател");
        addPublisherButton.addClickListener(l -> openPublisherGrid(new Publisher()));
        return addPublisherButton;
    }

    private void reloadGrid() {
        publisherGrid.deselectAll();
        publisherGrid.setItems(publisherService.findAll());
    }
}