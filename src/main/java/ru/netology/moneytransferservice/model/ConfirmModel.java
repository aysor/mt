package ru.netology.moneytransferservice.model;

public class ConfirmModel {
    private int operationId;
    private String code;

    public ConfirmModel(int operationId, String code) {
        this.operationId = operationId;
        this.code = code;
    }

    public int getOperationId() {
        return operationId;
    }

    public String getSecretCode() {
        return code;
    }

    @Override
    public String toString() {
        return String.format("%s {%s}", operationId, code);
    }
}
