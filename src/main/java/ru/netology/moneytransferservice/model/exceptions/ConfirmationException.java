package ru.netology.moneytransferservice.model.exceptions;

public class ConfirmationException extends RuntimeException {
    private int id;
    public ConfirmationException(int id, String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
