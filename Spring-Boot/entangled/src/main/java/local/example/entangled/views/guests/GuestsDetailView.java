package local.example.entangled.views.guests;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Key;
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
import local.example.entangled.data.entity.Guest;
import local.example.entangled.data.service.GuestService;
import local.example.entangled.views.MainLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@PageTitle("Guests Detail")
@Route(value = "guests-detail/:guestsID?/:action?(edit)", layout = MainLayout.class)
@RolesAllowed("admin")
public class GuestsDetailView
        extends Div
        implements BeforeEnterObserver {

    private final String GUESTS_EDIT_ROUTE_TEMPLATE = "guests-detail/%d/edit";

    private Grid<Guest> guestGrid = new Grid<>(Guest.class, false);

    private TextField username;
    private TextField password;
    private TextField role;

    private Button cancel = new Button("Cancel");
    private Button save = new Button("Save");

    private BeanValidationBinder<Guest> guestBeanValidationBinder;

    private Guest guest;

    private GuestService guestService;

    public GuestsDetailView(@Autowired GuestService guestService) {
        this.guestService = guestService;
        addClassNames("guests-detail-view", "flex", "flex-col", "h-full");

        SplitLayout splitLayout = new SplitLayout();
        splitLayout.setSizeFull();

        createGridLayout(splitLayout);
        createEditorLayout(splitLayout);

        add(splitLayout);

        guestGrid.addColumn(Guest::getUsername).setAutoWidth(true);
        guestGrid.addColumn(Guest::getPassword).setAutoWidth(true);
        guestGrid.addColumn(Guest::getRole).setAutoWidth(true);

        guestGrid.setItems(query -> guestService.pageable(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)))
                .stream());
        guestGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        guestGrid.setHeightFull();

        guestGrid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                UI.getCurrent().navigate(String.format(GUESTS_EDIT_ROUTE_TEMPLATE, event.getValue().getId()));
            } else {
                clearForm();
                UI.getCurrent().navigate(GuestsDetailView.class);
            }
        });

        guestBeanValidationBinder = new BeanValidationBinder<>(Guest.class);

        guestBeanValidationBinder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.guest == null) {
                    this.guest = new Guest();
                }
                guestBeanValidationBinder.writeBean(this.guest);

                guestService.update(this.guest);
                clearForm();
                refreshGrid();
                Notification.show("Guests details stored.");
                UI.getCurrent().navigate(GuestsDetailView.class);
            } catch (ValidationException validationException) {
                Notification.show("An exception happened while trying to store the users details.");
            }
        });
        save.addClickShortcut(Key.ENTER);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        String GUESTS_ID = "guestsID";
        Optional<Integer> guestsId = event.getRouteParameters().getInteger(GUESTS_ID);
        if (guestsId.isPresent()) {
            Optional<Guest> guestsFromBackend = guestService.get(guestsId.get());
            if (guestsFromBackend.isPresent()) {
                populateForm(guestsFromBackend.get());
            } else {
                Notification.show(String.format("The requested guests was not found, ID = %d", guestsId.get()), 3000,
                        Notification.Position.BOTTOM_START);
                refreshGrid();
                event.forwardTo(GuestsDetailView.class);
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
        username = new TextField("Username");
        password = new TextField("Password");
        role = new TextField("Role");
        Component[] fields = new Component[]{
                username,
                password,
                role
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
        wrapper.add(guestGrid);
    }

    private void refreshGrid() {
        guestGrid.select(null);
        guestGrid.getLazyDataView().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(Guest value) {
        this.guest = value;
        guestBeanValidationBinder.readBean(this.guest);
    }
}
