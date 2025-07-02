package ru.netology.moneytransferservice.service;

import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.logger.TransferLogger;
import ru.netology.moneytransferservice.model.*;
import ru.netology.moneytransferservice.model.exceptions.ConfirmationException;
import ru.netology.moneytransferservice.model.exceptions.InputDataException;
import ru.netology.moneytransferservice.repository.MoneyTransferRepository;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MoneyTransferService {
    private final MoneyTransferRepository transferRepository;
    private Map<Integer, MoneyTransferModel> operationsWaitConfirm = new ConcurrentHashMap<>();
    private TransferLogger logger;

    public MoneyTransferService(MoneyTransferRepository repository) {
        this.logger = TransferLogger.getInstance();
        this.transferRepository = repository;
    }

    public MoneyTransferServiceResponse transfer(MoneyTransferModel transferModel) throws InputDataException {
        int id = getIncrementedId();
        checkData(transferModel);
        String secretCode = secretCode = "0000";
        transferRepository.addCode(id, secretCode);
        operationsWaitConfirm.put(id, transferModel);
        Amount commission = new Amount(transferModel.getAmount().getValue().divide(new BigDecimal(100)),
                transferModel.getAmount().getCurrency());
        logger.logTransfer(transferModel, commission, id);
        return new MoneyTransferServiceResponse(id);
    }

    public MoneyTransferServiceResponse confirmOperation(ConfirmModel confirmModel) {
        if (!operationsWaitConfirm.containsKey(confirmModel.getOperationId())) {
            throw new ConfirmationException(confirmModel.getOperationId(), String.format(Messages.CONFIRM_CVV_NOT_EXIST, confirmModel.getOperationId(), confirmModel.getSecretCode()));
        }
        String secretCode = confirmModel.getSecretCode();
        String c = transferRepository.removeCode(confirmModel.getOperationId());
        if (c.equals(secretCode)) {
            //doTransfer(operationsWaitConfirm.remove(confirmModel.getOperationId()));
            MoneyTransferModel confirmed = operationsWaitConfirm.remove(confirmModel.getOperationId());
            transferRepository.doTransfer(transferRepository.getCardByNumber(confirmed.getCardFromNumber())
                    , transferRepository.getCardByNumber(confirmed.getCardToNumber())
                    , confirmed.getAmount().getValue());
        } else {
            throw new ConfirmationException(confirmModel.getOperationId(), String.format(Messages.CONFIRM_INCORRECT_CVV, confirmModel.getSecretCode(), confirmModel.getOperationId()));
        }
        logger.logConfirm(confirmModel);
        return new MoneyTransferServiceResponse(confirmModel.getOperationId());
    }

    public int getIncrementedId(){
        return transferRepository.getIncrementedOpId();
    }

    public boolean checkData(MoneyTransferModel transferModel) {
        if (!transferRepository.cardsContainsNumber(transferModel.getCardFromNumber())) {
            throw new InputDataException(transferRepository.getOpId(), Messages.MISSED_FROM_NUMBER);
        }
        if (!transferRepository.cardsContainsNumber(transferModel.getCardToNumber())) {
            throw new InputDataException(transferRepository.getOpId(), Messages.MISSED_TO_NUMBER);
        }

        Card fromDB = transferRepository.getCardByNumber(transferModel.getCardFromNumber());

        if (!fromDB.getCvv().equals(transferModel.getCardFromCVV())) {
            throw new InputDataException(transferRepository.getOpId(), Messages.INCORRECT_CVV);
        }
        if (!fromDB.getValidTill().equals(transferModel.getCardFromValidTill())) {
            throw new InputDataException(transferRepository.getOpId(), Messages.INCORRECT_VALID_TILL);
        }
        if (fromDB.getAmount().getValue().compareTo(transferModel.getAmount().getValue()) == -1) {
            throw new InputDataException(transferRepository.getOpId(), Messages.INSUFFICIENT_FUNDS);
        }
        if (!fromDB.getAmount().getCurrency().equals(transferModel.getAmount().getCurrency())) {
            throw new InputDataException(transferRepository.getOpId(), Messages.TRANSFER_IN_RUBLES);
        }
        return true;
    }
}
