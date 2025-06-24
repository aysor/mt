package ru.netology.moneytransferservice.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import ru.netology.moneytransferservice.logger.TransferLogger;
import ru.netology.moneytransferservice.model.*;
import ru.netology.moneytransferservice.model.exceptions.ConfirmationException;
import ru.netology.moneytransferservice.model.exceptions.InputDataException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MoneyTransferRepository {
    private final AtomicInteger operationId = new AtomicInteger();
    private Map<String, Card> cards = new ConcurrentHashMap<>();
    private Map<Integer, String> codes = new ConcurrentHashMap<>();
    private Map<Integer, MoneyTransferModel> operationsWaitConfirm = new ConcurrentHashMap<>();
    TransferLogger logger;

    public MoneyTransferRepository() {
        logger = TransferLogger.getInstance();
    }

    @PostConstruct
    public void initData(){
        Card c1 = new Card("1111111111111111", "111", "11/33", new Amount(new BigDecimal(111_111_123), "RUR"));
        Card c2 = new Card("2222222222222222", "222", "11/33", new Amount(new BigDecimal(1_111_123), "RUR"));
        Card c3 = new Card("3333333333333333", "333", "11/33", new Amount(new BigDecimal(11_111_223), "RUR"));
        cards.put(c1.getNumber(), c1);
        cards.put(c2.getNumber(), c2);
        cards.put(c3.getNumber(), c3);
    }

    public Response transfer(MoneyTransferModel transferModel) {
        int id = operationId.incrementAndGet();
        checkData(transferModel);
        //String secretCode = UUID.randomUUID().toString();
        //Stub for secret code:
        String secretCode = secretCode = "0000";
        codes.put(id, secretCode);
        operationsWaitConfirm.put(id, transferModel);
        Amount commission = new Amount(transferModel.getAmount().getValue().divide(new BigDecimal(100)),
                transferModel.getAmount().getCurrency());
        logger.logTransfer(transferModel, commission, id);
        return new Response(id);
    }

    public Response confirmOperation(ConfirmModel confirmModel) throws ConfirmationException{
        if (!operationsWaitConfirm.containsKey(confirmModel.getOperationId())) {
            throw new ConfirmationException(confirmModel.getOperationId(), String.format(Messages.CONFIRM_CVV_NOT_EXIST, confirmModel.getOperationId(), confirmModel.getSecretCode()));
        }
        String secretCode = confirmModel.getSecretCode();
        String c = codes.remove(confirmModel.getOperationId());
        if (c.equals(secretCode)) {
            //succeed
            doTransfer(operationsWaitConfirm.remove(confirmModel.getOperationId()));
        } else {
            throw new ConfirmationException(confirmModel.getOperationId(), String.format(Messages.CONFIRM_INCORRECT_CVV, confirmModel.getSecretCode(), confirmModel.getOperationId()));
        }
        logger.logConfirm(confirmModel);
        return new Response(confirmModel.getOperationId());
    }

    private void doTransfer(MoneyTransferModel transferModel) {
        Card from = cards.get(transferModel.getCardFromNumber());
        Card to = cards.get(transferModel.getCardToNumber());
        from.getAmount().setValue(from.getAmount().getValue().subtract(transferModel.getAmount().getValue()));
        to.getAmount().setValue(to.getAmount().getValue().add(transferModel.getAmount().getValue()));
    }

    private void checkData(MoneyTransferModel transferModel) {
        if (!cards.containsKey(transferModel.getCardFromNumber())) {
            throw new InputDataException(operationId.get(), Messages.MISSED_FROM_NUMBER);
        }
        if (!cards.containsKey(transferModel.getCardToNumber())) {
            throw new InputDataException(operationId.get(), Messages.MISSED_TO_NUMBER);
        }

        Card fromDB = cards.get(transferModel.getCardFromNumber());

        if(!fromDB.getCvv().equals(transferModel.getCardFromCVV())){
            throw new InputDataException(operationId.get(), Messages.INCORRECT_CVV);
        }
        if(!fromDB.getValidTill().equals(transferModel.getCardFromValidTill())){
            throw new InputDataException(operationId.get(), Messages.INCORRECT_VALID_TILL);
        }
        if (fromDB.getAmount().getValue().compareTo(transferModel.getAmount().getValue()) == -1) {
            throw new InputDataException(operationId.get(), Messages.INSUFFICIENT_FUNDS);
        }
        if (!fromDB.getAmount().getCurrency().equals(transferModel.getAmount().getCurrency())) {
            throw new InputDataException(operationId.get(), Messages.TRANSFER_IN_RUBLES);
        }
    }
}
