package local.example.entangled.views.field;

import com.vaadin.flow.component.customfield.CustomField;
import local.example.entangled.data.entity.Guest;

public class GuestField
        extends CustomField<Guest> {

    @Override
    protected Guest generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Guest guest) {

    }
}
