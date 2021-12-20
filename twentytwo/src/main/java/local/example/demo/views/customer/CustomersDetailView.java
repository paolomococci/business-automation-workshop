package local.example.demo.views.customer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
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

import java.util.List;
import java.util.Optional;
import javax.annotation.security.RolesAllowed;

import local.example.demo.data.entity.Address;
import local.example.demo.data.entity.Book;
import local.example.demo.data.entity.Customer;
import local.example.demo.data.service.AddressService;
import local.example.demo.data.service.BookService;
import local.example.demo.data.service.CustomerService;
import local.example.demo.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Customers Detail")
@Route(value = "customers-detail/:customerID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("admin")
public class CustomersDetailView extends Div implements BeforeEnterObserver {

    private final String CUSTOMER_EDIT_ROUTE_TEMPLATE = "customers-detail/%d/edit";

    private final Grid<Customer> customerGrid = new Grid<>(Customer.class, false);

    private TextField name;
    private TextField surname;
    private TextField email;
    private TextField phone;
    private DatePicker birthday;
    private TextField occupation;

    private ComboBox<Address> addressComboBox;
    private ComboBox<Book> bookComboBox;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Customer> customerBinder;

    private Customer customer;

    private CustomerService customerService;

    public CustomersDetailView(
            @Autowired CustomerService customerService,
            @Autowired AddressService addressService,
            @Autowired BookService bookService
    ) {
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
        createEditorLayout(
                splitLayout,
                addressService.list(),
                bookService.list()
        );

        add(splitLayout);

        this.customerGrid.addColumn(Customer::getName).setAutoWidth(true);
        this.customerGrid.addColumn(Customer::getSurname).setAutoWidth(true);
        this.customerGrid.addColumn(Customer::getEmail).setAutoWidth(true);
        this.customerGrid.addColumn(Customer::getPhone).setAutoWidth(true);
        this.customerGrid.addColumn(Customer::getBirthday).setAutoWidth(true);
        this.customerGrid.addColumn(Customer::getOccupation).setAutoWidth(true);
        this.customerGrid.addColumn(Customer::getAddresses).setAutoWidth(true);
        this.customerGrid.addColumn(Customer::getBooks).setAutoWidth(true);

        this.customerGrid.setItems(query -> this.customerService.pageable(
                PageRequest.of(
                    query.getPage(), 
                    query.getPageSize(), 
                    VaadinSpringDataHelpers.toSpringDataSort(query)
                ))
                .stream());
        this.customerGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.customerGrid.setHeightFull();

        this.customerGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(
                    String.format(CUSTOMER_EDIT_ROUTE_TEMPLATE, event.getValue().getId())
                );
            } else {
                clearForm();
                UI.getCurrent().navigate(CustomersDetailView.class);
            }
        });

        this.customerBinder = new BeanValidationBinder<>(Customer.class);

        this.customerBinder.bindInstanceFields(this);

        this.cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        this.save.addClickListener(e -> {
            try {
                if (this.customer == null) {
                    this.customer = new Customer();
                }
                this.customerBinder.writeBean(this.customer);

                this.customerService.update(this.customer);
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

    private void createEditorLayout(
            SplitLayout splitLayout,
            List<Address> addressList,
            List<Book> bookList
    ) {
        Div editorLayoutDiv = new Div();
        editorLayoutDiv.setClassName("flex flex-col");
        editorLayoutDiv.setWidth("400px");

        Div editorDiv = new Div();
        editorDiv.setClassName("p-l flex-grow");
        editorLayoutDiv.add(editorDiv);

        FormLayout formLayout = new FormLayout();

        this.name = new TextField("Name");
        this.surname = new TextField("Surname");
        this.email = new TextField("Email");
        this.phone = new TextField("Phone");
        this.birthday = new DatePicker("Birthday");
        this.occupation = new TextField("Occupation");

        this.addressComboBox = new ComboBox<>("Address");
        this.addressComboBox.setItems(addressList);
        this.addressComboBox.setItemLabelGenerator(Address::getStreet);

        this.bookComboBox = new ComboBox<>("Guest");
        this.bookComboBox.setItems(bookList);
        this.bookComboBox.setItemLabelGenerator(Book::getTitle);

        Component[] fields = new Component[] {
                this.name,
                this.surname,
                this.email,
                this.phone,
                this.birthday,
                this.occupation,
                this.addressComboBox,
                this.bookComboBox
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
        this.cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(
                this.save,
                this.cancel
        );
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(this.customerGrid);
    }

    private void refreshGrid() {
        this.customerGrid.select(null);
        this.customerGrid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Customer value) {
        this.customer = value;
        this.customerBinder.readBean(this.customer);
    }
}
