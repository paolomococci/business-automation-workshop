package local.example.demo.views.customer.field;

import com.vaadin.flow.component.customfield.CustomField;
import local.example.demo.data.entity.Address;
import local.example.demo.data.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressField extends CustomField<Address> {

    @Autowired
    AddressService addressService;

    public AddressField(String label) {
        setLabel(label);
    }

    @Override
    protected Address generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Address address) {

    }
}
