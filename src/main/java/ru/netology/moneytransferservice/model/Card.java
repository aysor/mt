package ru.netology.moneytransferservice.model;

public class Card {
    private String number;
    private String cvv;
    private String validTill;
    private Amount amount;

    public Card(String number, String cvv, String validTill, Amount amount) {
        this.number = number;
        this.cvv = cvv;
        this.validTill = validTill;
        this.amount = amount;
    }

    public String getNumber() {
        return number;
    }

    public String getCvv() {
        return cvv;
    }

    public String getValidTill() {
        return validTill;
    }

    public Amount getAmount() {
        return amount;
    }
}
