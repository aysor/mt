package ru.netology.moneytransferservice.model.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.netology.moneytransferservice.model.Response;

public class ErrorResponse extends Response {
    @JsonProperty("id")
    private int id;

    @JsonProperty("message")
    private String message;

    public ErrorResponse(int id, String message){
        super(id);
        this.id = id;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
