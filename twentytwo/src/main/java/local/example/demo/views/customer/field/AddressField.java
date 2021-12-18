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

    private final ComboBox<Address> address = new ComboBox<>();

    public AddressField(String label) {
        this.setLabel(label);
        this.address.setPlaceholder("Address");
        this.address.setRenderer(new TextRenderer<>(Address::toString));
        this.address.setItems(this.addressService.list());
        HorizontalLayout horizontalLayout = new HorizontalLayout(this.address);
        horizontalLayout.setFlexGrow(1.0, this.address);

        this.add(horizontalLayout);
    }

    @Override
    protected Address generateModelValue() {
        return this.address.getValue();
    }

    @Override
    protected void setPresentationValue(Address address) {
        this.address.setValue(address);
    }
}
