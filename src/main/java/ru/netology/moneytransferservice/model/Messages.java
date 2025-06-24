package ru.netology.moneytransferservice.model;

public class Messages {
    public final static String MISSED_FROM_NUMBER = "Номер карты отправителя отсутствует в базе";
    public final static String MISSED_TO_NUMBER = "Номер карты получателя отсутствует в базе";
    public static final String INCORRECT_CVV = "CVV отправителя указан неверно";
    public final static String INCORRECT_VALID_TILL = "Дата действия карты отправителя указана неверно";
    public final static String INSUFFICIENT_FUNDS = "Недостаточно средств";
    public final static String TRANSFER_IN_RUBLES = "Перевод осуществляется в рублях";

    public final static String CONFIRM_CVV_NOT_EXIST = "Операции #%s с секретным кодом {%s} не существует";
    public final static String CONFIRM_INCORRECT_CVV = "Неверный секретный код {%s} для операции #%s";
}
