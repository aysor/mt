package ru.netology.moneytransferservice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.netology.moneytransferservice.model.Response;
import ru.netology.moneytransferservice.model.exceptions.InputDataException;
import ru.netology.moneytransferservice.repository.MoneyTransferRepository;
import ru.netology.moneytransferservice.testModel.TestModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(MoneyTransferService.class)
@DisplayName("Money Transfer: Service level test")
public class MoneyTransferServiceTest {
    @Autowired
    private MoneyTransferService service;

    @MockitoBean
    private MoneyTransferRepository repository;

    @DisplayName("Transfer service: ok status")
    @Test
    public void transfer() {
        Response expected = new Response(1);
        Mockito.when(repository.transfer(TestModel.MODEL_OK)).thenReturn(new Response(1));
        Response actual = service.transfer(TestModel.MODEL_OK);
        assertEquals(expected, actual);
    }

    @DisplayName("Transfer service: cardFrom has incorrect CVV")
    @Test
    public void moneyTransferControllerCvvIncorrectTest() {
        Mockito.when(repository.transfer(TestModel.MODEL_CVV_INCORRECT)).thenThrow(new InputDataException(1, "CVV отправителя указан неверно"));

        assertThrows(InputDataException.class, () -> service.transfer(TestModel.MODEL_CVV_INCORRECT));
    }
}
