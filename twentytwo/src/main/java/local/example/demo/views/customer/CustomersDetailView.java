package local.example.demo.views.customer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
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
import local.example.demo.data.entity.Customer;
import local.example.demo.data.service.CustomerService;
import local.example.demo.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Customers Detail")
@Route(value = "customers-detail/:customerID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("admin")
public class CustomersDetailView extends Div implements BeforeEnterObserver {

    private final String CUSTOMER_EDIT_ROUTE_TEMPLATE = "customers-detail/%d/edit";

    private Grid<Customer> customerGrid = new Grid<>(Customer.class, false);

    private TextField name;
    private TextField surname;
    private TextField email;
    private TextField phone;
    private DatePicker birthday;
    private TextField occupation;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Customer> customerBinder;

    private Customer customer;

    private CustomerService customerService;

    public CustomersDetailView(@Autowired CustomerService customerService) {
        this.customerService = customerService;
        addClassNames(
            "customers-detail-view", 
            "flex", 
            "flex-col", 
            "h-full"
        );

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        customerGrid.addColumn("name").setAutoWidth(true);
        customerGrid.addColumn("surname").setAutoWidth(true);
        customerGrid.addColumn("email").setAutoWidth(true);
        customerGrid.addColumn("phone").setAutoWidth(true);
        customerGrid.addColumn("birthday").setAutoWidth(true);
        customerGrid.addColumn("occupation").setAutoWidth(true);
        customerGrid.setItems(query -> customerService.list(
                PageRequest.of(
                    query.getPage(), 
                    query.getPageSize(), 
                    VaadinSpringDataHelpers.toSpringDataSort(query)
                ))
                .stream());
        customerGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        customerGrid.setHeightFull();

        customerGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(
                    String.format(CUSTOMER_EDIT_ROUTE_TEMPLATE, event.getValue().getId())
                );
            } else {
                clearForm();
                UI.getCurrent().navigate(CustomersDetailView.class);
            }
        });

        customerBinder = new BeanValidationBinder<>(Customer.class);

        customerBinder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.customer == null) {
                    this.customer = new Customer();
                }
                customerBinder.writeBean(this.customer);

                customerService.update(this.customer);
                clearForm();
                refreshGrid();
                Notification.show("Customer details stored!");
                UI.getCurrent().navigate(CustomersDetailView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the customer details!");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String CUSTOMER_ID = "customerID";
        Optional<Integer> customerId = event.getRouteParameters().getInteger(CUSTOMER_ID);
        if (customerId.isPresent()) {
            Optional<Customer> customerFromBackend = customerService.get(customerId.get());
            if (customerFromBackend.isPresent()) {
                populateForm(customerFromBackend.get());
            } else {
                Notification.show(String.format(
                    "The requested customer was not found, ID = %d", 
                    customerId.get()), 
                    3000, 
                    Notification.Position.BOTTOM_START
                );
                refreshGrid();
                event.forwardTo(CustomersDetailView.class);
            }
        }
    }

    private void createEditorLayout(SplitLayout splitLayout) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();
        name = new TextField("Name");
        surname = new TextField("Surname");
        email = new TextField("Email");
        phone = new TextField("Phone");
        birthday = new DatePicker("Birthday");
        occupation = new TextField("Occupation");
        Component[] fields = new Component[] {
            name, 
            surname, 
            email, 
            phone, 
            birthday, 
            occupation
        };

        for (Component field : fields) {
            ((HasStyle) field).addClassName("full-width");
        }
        formLayout.add(fields);
        editorDiv.add(formLayout);
        createButtonLayout(editorLayoutDiv);

        splitLayout.addToSecondary(editorLayoutDiv);
    }

    private void createButtonLayout(Div editorLayoutDiv) {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setClassName(
            "w-full flex-wrap bg-contrast-5 py-s px-l"
        );
        buttonLayout.setSpacing(true);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save, cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(customerGrid);
    }

    private void refreshGrid() {
        customerGrid.select(null);
        customerGrid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Customer value) {
        this.customer = value;
        customerBinder.readBean(this.customer);
    }
}
