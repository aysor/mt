package ru.netology.moneytransferservice.model;

import lombok.Getter;
import lombok.Setter;

public class MoneyTransferModel {
    private String cardFromNumber;
    private String cardFromCVV;
    private String cardFromValidTill;

    private String cardToNumber;

    private Amount amount;

    public MoneyTransferModel(String cardFromNumber, String cardFromCVV, String cardFromValidTill, String cardToNumber, Amount amountToTransfer) {
        this.cardFromNumber = cardFromNumber;
        this.cardFromCVV = cardFromCVV;
        this.cardFromValidTill = cardFromValidTill;
        this.cardToNumber = cardToNumber;
        this.amount = amountToTransfer;
    }

    public String getCardFromNumber() {
        return cardFromNumber;
    }

    public String getCardFromCVV() {
        return cardFromCVV;
    }

    public String getCardFromValidTill() {
        return cardFromValidTill;
    }

    public String getCardToNumber() {
        return cardToNumber;
    }

    public Amount getAmount() {
        return amount;
    }

    public void setCardFromNumber(String cardFromNumber) {
        this.cardFromNumber = cardFromNumber;
    }

    public void setCardFromCVV(String cardFromCVV) {
        this.cardFromCVV = cardFromCVV;
    }

    public void setCardFromValidTill(String cardFromValidTill) {
        this.cardFromValidTill = cardFromValidTill;
    }

    public void setCardToNumber(String cardToNumber) {
        this.cardToNumber = cardToNumber;
    }

    public void setAmount(Amount amount) {
        this.amount = amount;
    }
}
