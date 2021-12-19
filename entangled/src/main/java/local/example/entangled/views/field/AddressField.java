package local.example.entangled.views.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import local.example.entangled.data.entity.Address;
import local.example.entangled.data.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressField
        extends CustomField<Address> {

    private final AddressService addressService;
    private final ComboBox<Address> addressComboBox;

    public AddressField(
            @Autowired AddressService addressService
    ) {
        this.addressService = addressService;
        this.addressComboBox = new ComboBox<>("Address");
        this.addressComboBox.setItems(this.addressService.list());
        this.addressComboBox.setItemLabelGenerator(Address::getStreet);
        this.add(this.addressComboBox);
    }

    @Override
    protected Address generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Address address) {

    }
}
