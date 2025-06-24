package ru.netology.moneytransferservice.repository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import ru.netology.moneytransferservice.model.Response;
import ru.netology.moneytransferservice.model.exceptions.InputDataException;
import ru.netology.moneytransferservice.testModel.TestModel;

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
    @Order(1)
    public void transfer() {
        Response expected = new Response(1);
        Response actual = repository.transfer(TestModel.MODEL_OK);
        assertEquals(expected, actual);
    }

    @DisplayName("Transfer repository: cardFrom has incorrect CVV")
    @Test
    @Order(2)
    public void moneyTransferControllerCvvIncorrectTest() {
        assertThrows(InputDataException.class, () -> repository.transfer(TestModel.MODEL_CVV_INCORRECT));
    }
}
