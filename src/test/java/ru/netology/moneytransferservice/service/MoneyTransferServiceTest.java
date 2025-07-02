package ru.netology.moneytransferservice.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.netology.moneytransferservice.model.Card;
import ru.netology.moneytransferservice.model.MoneyTransferServiceResponse;
import ru.netology.moneytransferservice.model.exceptions.InputDataException;
import ru.netology.moneytransferservice.repository.MoneyTransferRepository;
import ru.netology.moneytransferservice.testModel.TestModel;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;

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
        MoneyTransferServiceResponse expected = new MoneyTransferServiceResponse(1);

        Mockito.when(service.getIncrementedId()).thenReturn(1);
        Mockito.when(repository.cardsContainsNumber(TestModel.MODEL_OK.getCardFromNumber())).thenReturn(true);
        Mockito.when(repository.cardsContainsNumber(TestModel.MODEL_OK.getCardToNumber())).thenReturn(true);
        Mockito.when(repository.getCardByNumber(TestModel.MODEL_OK.getCardFromNumber()))
                .thenReturn(new Card(TestModel.MODEL_OK.getCardFromNumber()
                                   , TestModel.MODEL_OK.getCardFromCVV()
                                    , TestModel.MODEL_OK.getCardFromValidTill()
                                    , TestModel.MODEL_OK.getAmount()));

        MoneyTransferServiceResponse actual = service.transfer(TestModel.MODEL_OK);
        assertEquals(expected, actual);
    }

    @DisplayName("Transfer service: cardFrom has incorrect CVV")
    @Test
    public void moneyTransferControllerCvvIncorrectTest() {
        Mockito.when(repository.removeCode(1)).thenReturn("0000");
        assertThrows(InputDataException.class, () -> service.transfer(TestModel.MODEL_CVV_INCORRECT));
    }
}
