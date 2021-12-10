package local.example.welcome.views.contact;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;

import java.util.Optional;

import javax.annotation.security.RolesAllowed;

import local.example.welcome.data.entity.Person;
import local.example.welcome.data.service.PersonService;
import local.example.welcome.views.MainLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Staff")
@Route(value = "register/:personID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("user")
@Uses(Icon.class)
public class RegisterContactView extends Div implements BeforeEnterObserver {

    private final String SAMPLEPERSON_ID = "personID";
    private final String SAMPLEPERSON_EDIT_ROUTE_TEMPLATE = "register/%d/edit";

    private Grid<Person> personGrid = new Grid<>(Person.class, false);

    private TextField firstName;
    private TextField lastName;
    private TextField email;
    private TextField phone;
    private DatePicker dateOfBirth;
    private TextField occupation;
    private Checkbox important;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Person> binder;

    private Person person;

    private PersonService personService;

    public RegisterContactView(@Autowired PersonService personService) {
        this.personService = personService;
        addClassNames("flex", "flex-col", "h-full");

        SplitLayout splitLayoutUI = new SplitLayout();
        splitLayoutUI.setSizeFull();

        createGridLayout(splitLayoutUI);
        createEditorLayout(splitLayoutUI);

        add(splitLayoutUI);

        // Configure Grid
        personGrid.addColumn("firstName").setAutoWidth(true);
        personGrid.addColumn("lastName").setAutoWidth(true);
        personGrid.addColumn("email").setAutoWidth(true);
        personGrid.addColumn("phone").setAutoWidth(true);
        personGrid.addColumn("dateOfBirth").setAutoWidth(true);
        personGrid.addColumn("occupation").setAutoWidth(true);

        personGrid.setItems(query -> personService.readAll(
                PageRequest.of(
                        query.getPage(),
                        query.getPageSize(),
                        VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        personGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        personGrid.setHeightFull();

        personGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(
                        String.format(SAMPLEPERSON_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(RegisterContactView.class);
            }
        });

        binder = new BeanValidationBinder<>(Person.class);

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.person == null) {
                    this.person = new Person();
                }
                binder.writeBean(this.person);

                personService.update(this.person);
                clearForm();
                refreshGrid();
                Notification.show("Person details stored.");
                UI.getCurrent().navigate(RegisterContactView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the person details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Optional<Integer> personId = event.getRouteParameters().getInteger(SAMPLEPERSON_ID);
        if (personId.isPresent()) {
            Optional<Person> personFromBackend = personService.read(personId.get());
            if (personFromBackend.isPresent()) {
                populateForm(personFromBackend.get());
            } else {
                Notification.show(
                        String.format("The requested person was not found, ID = %d", personId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(RegisterContactView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayoutUI) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        firstName = new TextField("First Name");
        lastName = new TextField("Last Name");
        email = new TextField("Email");
        phone = new TextField("Phone");
        dateOfBirth = new DatePicker("Date Of Birth");
        occupation = new TextField("Occupation");
        important = new Checkbox("Important");
        important.getStyle().set("padding-top", "var(--lumo-space-m)");
        Component[] fields = new Component[] {
                firstName,
                lastName,
                email,
                phone,
                dateOfBirth,
                occupation,
                important
        };

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayoutUI.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayoutUI) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayoutUI.addToPrimary(wrapper);
        wrapper.add(personGrid);
    }

    private void refreshGrid() {
        personGrid.select(null);
        personGrid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Person value) {
        this.person = value;
        binder.readBean(this.person);
    }
}
