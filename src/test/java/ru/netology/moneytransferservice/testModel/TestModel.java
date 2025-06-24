package ru.netology.moneytransferservice.testModel;

import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.MoneyTransferModel;

import java.math.BigDecimal;

public class TestModel {
    public static final String ENDPOINT_TRANSFER = "/transfer";
    public static final String ENDPOINT_CONFIRM = "/confirmOperation";

    public  final static MoneyTransferModel MODEL_OK = new MoneyTransferModel("1111111111111111",
            "111",
            "11/33",
            "2222222222222222",
            new Amount(new BigDecimal(100), "RUR"));

    public final static MoneyTransferModel MODEL_CVV_INCORRECT = new MoneyTransferModel("1111111111111111",
            "123",
            "11/33",
            "2222222222222222",
            new Amount(new BigDecimal(100), "RUR"));
}
