package local.example.entangled.views.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import local.example.entangled.data.entity.Guest;
import local.example.entangled.data.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;

public class GuestField
        extends CustomField<Guest> {

    private final GuestService guestService;
    private final ComboBox<Guest> guestComboBox;

    public GuestField(
            @Autowired GuestService guestService
    ) {
        this.guestService = guestService;
        this.guestComboBox = new ComboBox<>("Guest");
        this.guestComboBox.setItems(this.guestService.list());
        this.guestComboBox.setItemLabelGenerator(Guest::getUsername);
        this.add(this.guestComboBox);
    }

    @Override
    protected Guest generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Guest guest) {

    }
}
