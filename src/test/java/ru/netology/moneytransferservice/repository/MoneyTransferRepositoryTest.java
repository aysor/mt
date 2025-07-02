package ru.netology.moneytransferservice.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.netology.moneytransferservice.model.Amount;
import ru.netology.moneytransferservice.model.Card;
import ru.netology.moneytransferservice.model.MoneyTransferServiceResponse;
import ru.netology.moneytransferservice.model.exceptions.InputDataException;
import ru.netology.moneytransferservice.testModel.TestModel;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(MoneyTransferRepository.class)
@DisplayName("Money Transfer: Repository level test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MoneyTransferRepositoryTest {

    @Autowired
    private MoneyTransferRepository repository;

    @DisplayName("Transfer repository: ok status")
    @Test
    public void doTransferTest() {
        Card from = new Card("1111111111111111", "111", "11/33", new Amount(new BigDecimal(100_000), "RUR"));
        Card to = new Card("2222222222222222", "222", "11/33", new Amount(new BigDecimal(90_000), "RUR"));
        BigDecimal valueFrom = new BigDecimal(900);
        BigDecimal valueTo = new BigDecimal(1000);
        repository.doTransfer(from, to, new BigDecimal(100));
        assertEquals(valueFrom, from.getAmount().getValue());
        assertEquals(valueTo, to.getAmount().getValue());
    }

}
