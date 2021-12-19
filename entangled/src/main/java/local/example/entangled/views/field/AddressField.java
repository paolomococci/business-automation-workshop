package local.example.entangled.views.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import local.example.entangled.data.entity.Address;

public class AddressField
        extends CustomField<Address> {

    private ComboBox<Address> addressComboBox;

    public AddressField(ComboBox<Address> addressComboBox) {
        this.addressComboBox = addressComboBox;
    }

    @Override
    protected Address generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Address address) {

    }
}
