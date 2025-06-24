package ru.netology.moneytransferservice.logger;

import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.ConfirmModel;
import ru.netology.moneytransferservice.model.MoneyTransferModel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransferLogger {
    private static volatile TransferLogger INSTANCE = null;
    private File logFile = new File("src/main/resources/moneyTransfer.log");
    private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static TransferLogger getInstance() {
        if (INSTANCE == null) {
            synchronized (TransferLogger.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TransferLogger();
                }
            }
        }
        return INSTANCE;
    }

    public void logTransfer(MoneyTransferModel transferModel, Amount commission, int operationId) {
        String message = String.format("[%s] Запрос перевода с карты #%s на карту #%s\n\tСумма перевода: %s%s;\tКомиссия: %s%s\tНомер транзакции: %s\n",
                dtf.format(LocalDateTime.now()),
                transferModel.getCardFromNumber(),
                transferModel.getCardToNumber(),
                transferModel.getAmount().getValue(),
                transferModel.getAmount().getCurrency(),
                commission.getValue(),
                commission.getCurrency(),
                operationId);
        writeLogFile(message);
    }

    public void logConfirm(ConfirmModel confirmModel) {
        String message = String.format("[%s] Транзакция #%s прошла успешно.\n",
                dtf.format(LocalDateTime.now()),
                confirmModel.getOperationId());
        writeLogFile(message);
    }

    private void writeLogFile(String log) {
        try (FileWriter fw = new FileWriter(logFile, true)) {
            fw.write(log);
        } catch (IOException e) {

        }
    }
}
