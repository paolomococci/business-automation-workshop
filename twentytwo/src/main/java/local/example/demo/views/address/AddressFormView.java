package local.example.demo.views.address;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import javax.annotation.security.RolesAllowed;

import local.example.demo.data.entity.Address;
import local.example.demo.data.service.AddressService;
import local.example.demo.views.MainLayout;

@PageTitle("Address Form")
@Route(value = "address-form", layout = MainLayout.class)
@RolesAllowed("user")
public class AddressFormView extends Div {

    private final TextField street = new TextField("Street address");
    private final TextField postalCode = new TextField("Postal code");
    private final TextField city = new TextField("City");
    private final ComboBox<String> state = new ComboBox<>("State");
    private final ComboBox<String> country = new ComboBox<>("Country");

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final Binder<Address> addressBinder = new Binder<>(Address.class);

    public AddressFormView(AddressService addressService) {
        addClassName("address-form-view");

        add(createTitle());
        add(createFormLayout());
        add(createButtonLayout());

        addressBinder.bindInstanceFields(this);

        clearForm();

        cancel.addClickListener(e -> clearForm());
        save.addClickListener(e -> {
            addressService.update(addressBinder.getBean());
            Notification.show(addressBinder.getBean().getClass().getSimpleName() + " stored!");
            clearForm();
        });
    }

    private Component createTitle() {
        return new H3("Address");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(street, 2);
        postalCode.setPattern("\\d*");
        postalCode.setPreventInvalidInput(true);
        country.setItems("Country 1", "Country 2", "Country 3");
        state.setItems("State A", "State B", "State C", "State D");
        formLayout.add(postalCode, city, state, country);
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

    private void clearForm() {
        this.addressBinder.setBean(new Address());
    }
}
