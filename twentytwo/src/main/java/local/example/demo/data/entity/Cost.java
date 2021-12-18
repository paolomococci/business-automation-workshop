package local.example.demo.data.entity;

import local.example.demo.data.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Cost extends AbstractEntity {

    private String amount;
    private String currency;

    public Cost(
            String amount,
            String currency
    ) {
        this.amount = amount;
        this.currency = currency;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
