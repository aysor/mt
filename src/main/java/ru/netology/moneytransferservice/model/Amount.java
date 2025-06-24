package ru.netology.moneytransferservice.model;

import java.math.BigDecimal;

public class Amount {
    private BigDecimal value;
    private String currency;

    public Amount(BigDecimal amount, String currency) {
        this.value = amount;
        this.currency = currency;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public void setValue(BigDecimal value) {
        this.value = value.divide(new BigDecimal(100));
    }

    @Override
    public String toString() {
        return String.format("%s%s", value, currency);
    }
}
