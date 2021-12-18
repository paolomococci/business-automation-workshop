package local.example.demo.views.customer.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class PhoneNumberField extends CustomField<String> {

    private final ComboBox<String> countryCode = new ComboBox<>();
    private final TextField number = new TextField();

    public PhoneNumberField(String label) {
        this.setLabel(label);
        this.countryCode.setWidth("120px");
        this.countryCode.setPlaceholder("Country");
        this.countryCode.setPattern("\\+\\d*");
        this.countryCode.setPreventInvalidInput(true);
        this.countryCode.setItems("+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225");
        this.countryCode.addCustomValueSetListener(e -> this.countryCode.setValue(e.getDetail()));
        this.number.setPattern("\\d*");
        this.number.setPreventInvalidInput(true);
        HorizontalLayout horizontalLayout = new HorizontalLayout(
                this.countryCode,
                this.number
        );
        horizontalLayout.setFlexGrow(1.0, this.number);

        this.add(horizontalLayout);
    }

    @Override
    protected String generateModelValue() {
        if (this.countryCode.getValue() != null && this.number.getValue() != null) {
            return this.countryCode.getValue() + " " + this.number.getValue();
        }
        return "";
    }

    @Override
    protected void setPresentationValue(String phoneNumber) {
        String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
        if (parts.length == 1) {
            this.countryCode.clear();
            this.number.setValue(parts[0]);
        } else if (parts.length == 2) {
            this.countryCode.setValue(parts[0]);
            this.number.setValue(parts[1]);
        } else {
            this.countryCode.clear();
            this.number.clear();
        }
    }
}
