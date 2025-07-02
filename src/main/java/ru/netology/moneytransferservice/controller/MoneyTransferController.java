package ru.netology.moneytransferservice.controller;

import org.springframework.web.bind.annotation.*;
import ru.netology.moneytransferservice.model.ConfirmModel;
import ru.netology.moneytransferservice.model.MoneyTransferModel;
import ru.netology.moneytransferservice.model.MoneyTransferServiceResponse;
import ru.netology.moneytransferservice.model.exceptions.InputDataException;
import ru.netology.moneytransferservice.service.MoneyTransferService;

@CrossOrigin("https://serp-ya.github.io/")
@RestController
public class MoneyTransferController {
    private final MoneyTransferService service;

    public MoneyTransferController(MoneyTransferService service) {
        this.service = service;
    }

    @PostMapping("/transfer")
    public MoneyTransferServiceResponse transfer(@RequestBody MoneyTransferModel transferModel) throws InputDataException {
        return service.transfer(transferModel);
    }

    @PostMapping("/confirmOperation")
    public MoneyTransferServiceResponse confirmOperation(@RequestBody ConfirmModel confirmModel) {
        return service.confirmOperation(confirmModel);
    }
}
