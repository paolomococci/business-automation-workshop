package local.example.entangled.views.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import local.example.entangled.data.entity.Address;
import local.example.entangled.data.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressField
        extends CustomField<Address> {

    private Address address;
    private final AddressService addressService;
    private final ComboBox<Address> addressComboBox;

    public AddressField(
            AddressService addressService
    ) {
        this.addressService = addressService;
        this.addressComboBox = new ComboBox<>("Address");
        this.addressComboBox.setItems(this.addressService.list());
        this.addressComboBox.setItemLabelGenerator(Address::getStreet);
        this.add(this.addressComboBox);
    }

    @Override
    protected Address generateModelValue() {
        return this.addressComboBox.getValue();
    }

    @Override
    protected void setPresentationValue(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
