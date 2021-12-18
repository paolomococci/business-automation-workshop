package local.example.demo.views.customer.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import local.example.demo.data.entity.Address;
import local.example.demo.data.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;

public class AddressField extends CustomField<Address> {

    @Autowired
    AddressService addressService;

    public AddressField(String label) {
        setLabel(label);
        ComboBox<Address> address = new ComboBox<>();
        address.setPlaceholder("Address");
        address.setRenderer(new TextRenderer<>(Address::toString));
        address.setItems(this.addressService.list());
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
