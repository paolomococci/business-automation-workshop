package local.example.demo.views.customer.field;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class PhoneNumberField extends CustomField<String> {

    private final ComboBox<String> countryCode = new ComboBox<>();
    private final TextField number = new TextField();

    public PhoneNumberField(String label) {
        setLabel(label);
        countryCode.setWidth("120px");
        countryCode.setPlaceholder("Country");
        countryCode.setPattern("\\+\\d*");
        countryCode.setPreventInvalidInput(true);
        countryCode.setItems("+354", "+91", "+62", "+98", "+964", "+353", "+44", "+972", "+39", "+225");
        countryCode.addCustomValueSetListener(e -> countryCode.setValue(e.getDetail()));
        number.setPattern("\\d*");
        number.setPreventInvalidInput(true);
        HorizontalLayout layout = new HorizontalLayout(countryCode, number);
        layout.setFlexGrow(1.0, number);
        add(layout);
    }

    @Override
    protected String generateModelValue() {
        if (countryCode.getValue() != null && number.getValue() != null) {
            return countryCode.getValue() + " " + number.getValue();
        }
        return "";
    }

    @Override
    protected void setPresentationValue(String phoneNumber) {
        String[] parts = phoneNumber != null ? phoneNumber.split(" ", 2) : new String[0];
        if (parts.length == 1) {
            countryCode.clear();
            number.setValue(parts[0]);
        } else if (parts.length == 2) {
            countryCode.setValue(parts[0]);
            number.setValue(parts[1]);
        } else {
            countryCode.clear();
            number.clear();
        }
    }
}
