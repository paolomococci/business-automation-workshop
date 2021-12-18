package local.example.demo.views.book.field;

import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import local.example.demo.data.entity.Cost;

public class CostField extends CustomField<Cost> {

    private final TextField amount;
    private final Select<String> currency;

    public CostField() {
        this.amount = new TextField();
        this.currency = new Select<>();
        this.currency.setItems(
                "USD",
                "EUR",
                "JPY",
                "GBP",
                "AUD",
                "CAD",
                "CHF",
                "CNY",
                "HKD",
                "NZD",
                "SEK",
                "KRW",
                "SGD",
                "NOK",
                "MXN",
                "INR",
                "RUB",
                "ZAR",
                "TRY",
                "BRL",
                "TWD",
                "DKK"
        );
        this.currency.setWidth("6em");

        HorizontalLayout horizontalLayout = new HorizontalLayout(
                this.amount,
                this.currency
        );

        this.add(horizontalLayout);
    }

    @Override
    protected Cost generateModelValue() {
        return null;
    }

    @Override
    protected void setPresentationValue(Cost cost) {

    }
}
