package ru.netology.moneytransferservice.model.exceptions;

public class TransferException extends RuntimeException {
    private int id;
    public TransferException(String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
