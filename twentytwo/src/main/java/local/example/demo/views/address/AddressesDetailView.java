package local.example.demo.views.address;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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

import local.example.demo.data.entity.Address;
import local.example.demo.data.service.AddressService;
import local.example.demo.views.MainLayout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Addresses Detail")
@Route(value = "address-detail/:addressID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("admin")
public class AddressesDetailView extends Div implements BeforeEnterObserver {

    @Autowired
    AddressService addressService;

    private final String ADDRESS_EDIT_ROUTE_TEMPLATE = "address-detail/%d/edit";

    private final Grid<Address> addressGrid = new Grid<>(Address.class, false);

    private TextField street;
    private TextField postalCode;
    private TextField city;
    private TextField state;
    private TextField country;

    private final Button cancel = new Button("Cancel");
    private final Button save = new Button("Save");

    private final BeanValidationBinder<Address> addressBinder;

    private Address address;

    public AddressesDetailView() {
        addClassNames(
            "addresses-detail-view", 
            "flex", 
            "flex-col", 
            "h-full"
        );

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        this.addressGrid.addColumn(Address::getStreet).setAutoWidth(true);
        this.addressGrid.addColumn(Address::getPostalCode).setAutoWidth(true);
        this.addressGrid.addColumn(Address::getCity).setAutoWidth(true);
        this.addressGrid.addColumn(Address::getCity).setAutoWidth(true);
        this.addressGrid.addColumn(Address::getCountry).setAutoWidth(true);

        this.addressGrid.setItems(query -> this.addressService.list(
                PageRequest.of(query.getPage(), 
                query.getPageSize(), 
                VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());

        this.addressGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        this.addressGrid.setHeightFull();

        this.addressGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(
                    String.format(ADDRESS_EDIT_ROUTE_TEMPLATE, 
                    event.getValue().getId())
                );
            } else {
                clearForm();
                UI.getCurrent().navigate(AddressesDetailView.class);
            }
        });

        this.addressBinder = new BeanValidationBinder<>(Address.class);

        this.addressBinder.bindInstanceFields(this);

        this.cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        this.save.addClickListener(e -> {
            try {
                if (this.address == null) {
                    this.address = new Address();
                }
                this.addressBinder.writeBean(this.address);

                this.addressService.update(this.address);
                clearForm();
                refreshGrid();
                Notification.show("Address details stored!");
                UI.getCurrent().navigate(AddressesDetailView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the address details!");
            }
        });

    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String ADDRESS_ID = "addressID";
        Optional<Integer> addressId = event.getRouteParameters().getInteger(ADDRESS_ID);
        if (addressId.isPresent()) {
            Optional<Address> addressFromBackend = addressService.get(addressId.get());
            if (addressFromBackend.isPresent()) {
                populateForm(addressFromBackend.get());
            } else {
                Notification.show(String.format("The requested address was not found, ID = %d", addressId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(AddressesDetailView.class);
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
        this.street = new TextField("Street");
        this.postalCode = new TextField("Postal Code");
        this.city = new TextField("City");
        this.state = new TextField("State");
        this.country = new TextField("Country");
        Component[] fields = new Component[] {
                this.street,
                this.postalCode,
                this.city,
                this.state,
                this.country
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
        wrapper.add(this.addressGrid);
    }

    private void refreshGrid() {
        this.addressGrid.select(null);
        this.addressGrid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Address value) {
        this.address = value;
        this.addressBinder.readBean(this.address);
    }
}
