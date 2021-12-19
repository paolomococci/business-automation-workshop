package local.example.entangled.views.field;

import com.vaadin.flow.component.customfield.CustomField;
import local.example.entangled.data.entity.Address;

public class AddressField
        extends CustomField<Address> {

    @Override
    protected Address generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Address address) {

    }
}
