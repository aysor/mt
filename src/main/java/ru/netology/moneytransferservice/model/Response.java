package ru.netology.moneytransferservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Response {
    @JsonProperty("operationId")
    private int operationId;

    public Response(int operationId){
        this.operationId = operationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response response = (Response) o;
        return operationId == response.operationId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(operationId);
    }

    @Override
    public String toString() {
        return String.format("OperationId=%s", operationId);
    }
}
