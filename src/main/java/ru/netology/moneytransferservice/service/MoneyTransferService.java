package ru.netology.moneytransferservice.service;

import org.springframework.stereotype.Service;
import ru.netology.moneytransferservice.model.ConfirmModel;
import ru.netology.moneytransferservice.model.MoneyTransferModel;
import ru.netology.moneytransferservice.model.Response;
import ru.netology.moneytransferservice.model.exceptions.InputDataException;
import ru.netology.moneytransferservice.repository.MoneyTransferRepository;

@Service
public class MoneyTransferService {
    private final MoneyTransferRepository transferRepository;
    public  MoneyTransferService(MoneyTransferRepository repository){
        this.transferRepository = repository;
    }

    public Response transfer(MoneyTransferModel transferModel) throws InputDataException {
        return transferRepository.transfer(transferModel);
    }

    public Response confirmOperation(ConfirmModel confirmModel) {
        return transferRepository.confirmOperation(confirmModel);
    }
}
