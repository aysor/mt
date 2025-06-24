package ru.netology.moneytransferservice.model.exceptions;

public class InputDataException extends RuntimeException {
    private int id;
    public InputDataException(int id, String message) {
        super(message);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
