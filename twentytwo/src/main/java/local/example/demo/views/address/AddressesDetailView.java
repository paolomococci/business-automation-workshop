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

    private final String ADDRESS_ID = "addressID";
    private final String ADDRESS_EDIT_ROUTE_TEMPLATE = "address-detail/%d/edit";

    private Grid<Address> addressGrid = new Grid<>(Address.class, false);

    private TextField street;
    private TextField postalCode;
    private TextField city;
    private TextField state;
    private TextField country;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Address> addressBinder;

    private Address address;

    private AddressService addressService;

    public AddressesDetailView(@Autowired AddressService addressService) {
        this.addressService = addressService;
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

        addressGrid.addColumn("street").setAutoWidth(true);
        addressGrid.addColumn("postalCode").setAutoWidth(true);
        addressGrid.addColumn("city").setAutoWidth(true);
        addressGrid.addColumn("state").setAutoWidth(true);
        addressGrid.addColumn("country").setAutoWidth(true);
        addressGrid.setItems(query -> addressService.list(
                PageRequest.of(query.getPage(), 
                query.getPageSize(), 
                VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        addressGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        addressGrid.setHeightFull();

        addressGrid.asSingleSelect().addValueChangeListener(event -> {
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

        addressBinder = new BeanValidationBinder<>(Address.class);

        addressBinder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.address == null) {
                    this.address = new Address();
                }
                addressBinder.writeBean(this.address);

                addressService.update(this.address);
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
        street = new TextField("Street");
        postalCode = new TextField("Postal Code");
        city = new TextField("City");
        state = new TextField("State");
        country = new TextField("Country");
        Component[] fields = new Component[]{street, postalCode, city, state, country};

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
        wrapper.add(addressGrid);
    }

    private void refreshGrid() {
        addressGrid.select(null);
        addressGrid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Address value) {
        this.address = value;
        addressBinder.readBean(this.address);

    }
}
