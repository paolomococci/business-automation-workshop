package local.example.demo.views.customer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import local.example.demo.data.entity.Address;
import local.example.demo.data.entity.Book;
import local.example.demo.data.entity.Customer;
import local.example.demo.data.service.AddressService;
import local.example.demo.data.service.BookService;
import local.example.demo.data.service.CustomerService;
import local.example.demo.views.MainLayout;
import local.example.demo.views.customer.field.PhoneNumberField;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@PageTitle("Customer Form")
@Route(value = "customer-form", layout = MainLayout.class)
@RolesAllowed("user")
@Uses(Icon.class)
public class CustomerFormView extends Div {

    private final TextField name = new TextField("Name");
    private final TextField surname = new TextField("Surname");
    private final EmailField email = new EmailField("Email");
    private final DatePicker birthday = new DatePicker("Birthday");
    private final PhoneNumberField phoneNumber = new PhoneNumberField("Phone");
    private final TextField occupation = new TextField("Occupation");

    private final ComboBox<Address> addressComboBox = new ComboBox<>("Address");
    private final ComboBox<Book> bookComboBox = new ComboBox<>("Book");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final Binder<Customer> customerBinder = new Binder<>(Customer.class);

    public CustomerFormView(
            @Autowired CustomerService customerService,
            @Autowired AddressService addressService,
            @Autowired BookService bookService
    ) {
        addClassName("customer-form-view");

        add(createTitle());
        add(createFormLayout(
                addressService.list(),
                bookService.list()
        ));
        add(createButtonLayout());

        customerBinder.bindInstanceFields(this);
        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            customerService.update(customerBinder.getBean());
            Notification.show(customerBinder.getBean().getClass().getSimpleName() + " details stored!");
            clearForm();
        });
    }

    private void clearForm() {
        customerBinder.setBean(new Customer());
    }

    private Component createTitle() {
        return new H3("Customer information fields");
    }

    private Component createFormLayout(
            List<Address> addressList,
            List<Book> bookList
    ) {
        FormLayout formLayout = new FormLayout();

        email.setErrorMessage("Please enter a valid email address");

        addressComboBox.setItems(addressList);
        addressComboBox.setItemLabelGenerator(Address::getStreet);

        bookComboBox.setItems(bookList);
        bookComboBox.setItemLabelGenerator(Book::getTitle);

        formLayout.add(
                name,
                surname,
                birthday,
                phoneNumber,
                email,
                occupation,
                addressComboBox,
                bookComboBox
        );
        return formLayout;
    }

    private Component createButtonLayout() {
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.addClassName("button-layout");
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonLayout.add(save);
        buttonLayout.add(cancel);
        return buttonLayout;
    }
}
