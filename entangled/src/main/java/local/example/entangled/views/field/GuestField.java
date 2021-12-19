package local.example.entangled.views.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import local.example.entangled.data.entity.Guest;
import local.example.entangled.data.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;

public class GuestField
        extends CustomField<Guest> {

    private Guest guest;
    private final GuestService guestService;
    private final ComboBox<Guest> guestComboBox;

    public GuestField(
            GuestService guestService
    ) {
        this.guestService = guestService;
        this.guestComboBox = new ComboBox<>("Guest");
        this.guestComboBox.setItems(this.guestService.list());
        this.guestComboBox.setItemLabelGenerator(Guest::getUsername);
        HorizontalLayout horizontalLayout = new HorizontalLayout(this.guestComboBox);
        horizontalLayout.setFlexGrow(1.0, this.guestComboBox);

        this.add(horizontalLayout);
    }

    @Override
    protected Guest generateModelValue() {
        return this.guestComboBox.getValue();
    }

    @Override
    protected void setPresentationValue(Guest guest) {
        this.guest = guest;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }
}
