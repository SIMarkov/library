package pu.project.app.views.author;

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

import pu.project.app.models.Author;
import pu.project.app.services.AuthorService;
import pu.project.app.views.MainLayout;

@PageTitle("Автори")
@Route(value = "author", layout = MainLayout.class)
public class AuthorView extends VerticalLayout {
    private AuthorService authorService;
    private Grid<Author> authorGrid;
    private Button editButton;
    private Button deleteButton;

    public AuthorView(final AuthorService authorService) {
        this.authorService = authorService;
        initContent();
    }

    private void initContent() {
        HorizontalLayout buttons = new HorizontalLayout(createAddButton(), createEditButton(), createDeleteButton());
        add(buttons);
        add(createGrid());
        Accordion accordion = new Accordion();
        accordion.setSizeFull();
    }

    private Grid<Author> createGrid() {
        authorGrid = new AuthorGrid();
        authorGrid.setMinHeight("300px");
        authorGrid.addItemDoubleClickListener(l -> openGrid(l.getItem()));
        SingleSelect<Grid<Author>, Author> singleSelect = authorGrid.asSingleSelect();
        singleSelect.addValueChangeListener(l -> {
            Author value = l.getValue();
            boolean enabled = value != null;
            editButton.setEnabled(enabled);
            deleteButton.setEnabled(enabled);
        });
        reloadGrid();
        return authorGrid;
    }

    private Button createDeleteButton() {
        deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.setEnabled(false);
        deleteButton.setTooltipText("Изтрий автор");
        deleteButton.addClickListener(l -> {
            Dialog dialog = new Dialog();
            Author value = authorGrid.asSingleSelect().getValue();
            dialog.setHeaderTitle("Сигурни ли сте, че искате да изтриете автор: " + value.getName());
            Button ok = new Button("Да", ll -> {
                authorService.remove(value.getId());
                reloadGrid();
                dialog.close();
            });
            ok.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            Button no = new Button("Не", ll -> dialog.close());
            dialog.getFooter().add(no, ok);
            dialog.open();
        });
        return deleteButton;
    }

    private Button createEditButton() {
        editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        editButton.addClickListener(l -> openGrid(authorGrid.asSingleSelect().getValue()));
        editButton.setEnabled(false);
        editButton.setTooltipText("Промени автор");
        return editButton;
    }

    private void openGrid(Author author) {
        AuthorDialog dialog = new AuthorDialog(author, authorService);
        dialog.addSaveClickListener(ll -> {
            reloadGrid();
            dialog.close();
        });
        dialog.open();
    }

    private Button createAddButton() {
        Button addButton = new Button(new Icon(VaadinIcon.PENCIL));
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        addButton.setTooltipText("Добави автор");
        addButton.addClickListener(l -> openGrid(new Author()));
        return addButton;
    }

    private void reloadGrid() {
        authorGrid.deselectAll();
        authorGrid.setItems(authorService.findAll());
    }
}