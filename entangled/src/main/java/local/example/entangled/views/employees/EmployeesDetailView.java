package local.example.entangled.views.employees;

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
import local.example.entangled.data.entity.Employee;
import local.example.entangled.data.service.AddressService;
import local.example.entangled.data.service.EmployeeService;
import local.example.entangled.data.service.GuestService;
import local.example.entangled.views.MainLayout;
import local.example.entangled.views.field.AddressField;
import local.example.entangled.views.field.GuestField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Employees Detail")
@Route(value = "employees-detail/:employeeID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("admin")
public class EmployeesDetailView
        extends Div
        implements BeforeEnterObserver {

    private final String EMPLOYEE_EDIT_ROUTE_TEMPLATE = "employees-detail/%d/edit";

    private Grid<Employee> employeeGrid = new Grid<>(Employee.class, false);

    private TextField nameTextField;
    private TextField surnameTextField;
    private TextField emailTextField;
    private TextField phoneTextField;
    private DatePicker birthdayDatePicker;
    private TextField assignmentTextField;
    private AddressField addressField;
    private GuestField guestField;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Employee> employeeBeanValidationBinder;

    private Employee employee;

    private final EmployeeService employeeService;
    private final AddressService addressService;
    private final GuestService guestService;

    public EmployeesDetailView(
            @Autowired EmployeeService employeeService,
            @Autowired AddressService addressService,
            @Autowired GuestService guestService
            ) {
        this.employeeService = employeeService;
        this.addressService = addressService;
        this.guestService = guestService;


        addClassNames("employees-detail-view", "flex", "flex-col", "h-full");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        this.employeeGrid.addColumn(Employee::getName).setAutoWidth(true);
        this.employeeGrid.addColumn(Employee::getSurname).setAutoWidth(true);
        this.employeeGrid.addColumn(Employee::getAddress).setAutoWidth(true);
        this.employeeGrid.addColumn(Employee::getPhone).setAutoWidth(true);
        this.employeeGrid.addColumn(Employee::getBirthday).setAutoWidth(true);
        this.employeeGrid.addColumn(Employee::getAssignment).setAutoWidth(true);
        this.employeeGrid.addColumn(Employee::getAddress).setAutoWidth(true);
        this.employeeGrid.addColumn(Employee::getGuest).setAutoWidth(true);

        employeeGrid.setItems(query -> this.employeeService.pageable(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        this.employeeGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.employeeGrid.setHeightFull();

        this.employeeGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(EMPLOYEE_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(EmployeesDetailView.class);
            }
        });

        this.employeeBeanValidationBinder = new BeanValidationBinder<>(Employee.class);

        this.employeeBeanValidationBinder.bindInstanceFields(this);

        this.cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        this.save.addClickListener(e -> {
            try {
                if (this.employee == null) {
                    this.employee = new Employee();
                }
                this.employeeBeanValidationBinder.writeBean(this.employee);

                this.employeeService.update(this.employee);
                clearForm();
                refreshGrid();
                Notification.show("Employee details stored.");
                UI.getCurrent().navigate(EmployeesDetailView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the employee details.");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String EMPLOYEE_ID = "employeeID";
        Optional<Integer> employeeId = event.getRouteParameters().getInteger(EMPLOYEE_ID);
        if (employeeId.isPresent()) {
            Optional<Employee> employeeFromBackend = employeeService.get(employeeId.get());
            if (employeeFromBackend.isPresent()) {
                populateForm(employeeFromBackend.get());
            } else {
                Notification.show(String.format("The requested employee was not found, ID = %d", employeeId.get()),
                        3000, Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(EmployeesDetailView.class);
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
        this.nameTextField = new TextField("Name");
        this.surnameTextField = new TextField("Surname");
        this.emailTextField = new TextField("Email");
        this.phoneTextField = new TextField("Phone");
        this.birthdayDatePicker = new DatePicker("Birthday");
        this.assignmentTextField = new TextField("Assignment");
        this.addressField = new AddressField(this.addressService);
        this.guestField = new GuestField(this.guestService);
        Component[] fields = new Component[]{
                this.nameTextField,
                this.surnameTextField,
                this.emailTextField,
                this.phoneTextField,
                this.birthdayDatePicker,
                this.assignmentTextField,
                this.addressField,
                this.guestField
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
        buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
        buttonLayout.setSpacing(true);
        this.cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        this.save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(this.save, this.cancel);
        editorLayoutDiv.add(buttonLayout);
    }

    private void createGridLayout(SplitLayout splitLayout) {
        Div wrapper = new Div();
        wrapper.setId("grid-wrapper");
        wrapper.setWidthFull();
        splitLayout.addToPrimary(wrapper);
        wrapper.add(this.employeeGrid);
    }

    private void refreshGrid() {
        this.employeeGrid.select(null);
        this.employeeGrid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Employee value) {
        this.employee = value;
        this.employeeBeanValidationBinder.readBean(this.employee);
    }
}
