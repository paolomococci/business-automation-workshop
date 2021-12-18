package local.example.demo.views.book.field;

import com.vaadin.flow.component.customfield.CustomField;
import local.example.demo.data.entity.Cost;

public class CostField extends CustomField<Cost> {

    @Override
    protected Cost generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Cost cost) {

    }
}
