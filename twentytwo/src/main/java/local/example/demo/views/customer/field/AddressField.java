package local.example.demo.views.customer.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import local.example.demo.data.entity.Address;
import local.example.demo.data.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressField extends CustomField<Address> {

    @Autowired
    AddressService addressService;

    private final ComboBox<String> address = new ComboBox<>();

    public AddressField(String label) {
        setLabel(label);
        HorizontalLayout horizontalLayout = new HorizontalLayout(address);
        horizontalLayout.setFlexGrow(1.0, address);
        add(horizontalLayout);
    }

    @Override
    protected Address generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Address address) {

    }
}
