package ru.netology.moneytransferservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.netology.moneytransferservice.model.Messages;
import ru.netology.moneytransferservice.model.Response;
import ru.netology.moneytransferservice.model.exceptions.InputDataException;
import ru.netology.moneytransferservice.service.MoneyTransferService;
import ru.netology.moneytransferservice.testModel.TestModel;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MoneyTransferController.class)
@DisplayName("Money Transfer: Controller level test")
public class MoneyTransferControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MoneyTransferService service;

    @Test
    @DisplayName("Transfer controller: ok status")
    public void moneyTransferControllerTest() throws Exception {
        Mockito.when(service.transfer(TestModel.MODEL_OK)).thenReturn(new Response(1));
        String requestBody = objectMapper.writeValueAsString(TestModel.MODEL_OK);
        mockMvc.perform(post(TestModel.ENDPOINT_TRANSFER).contentType("application/json")
                        .content(requestBody))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Transfer controller: cardFrom has incorrect CVV")
    public void moneyTransferControllerCvvIncorrectTest(){
        Mockito.when(service.transfer(TestModel.MODEL_CVV_INCORRECT))
                .thenThrow(new InputDataException(1, "qwe"));

        MoneyTransferService myService = Mockito.mock(MoneyTransferService.class);
        Mockito.when(myService.transfer(TestModel.MODEL_CVV_INCORRECT)).thenThrow(new InputDataException(1, Messages.INCORRECT_CVV));

        assertThrows(InputDataException.class, () -> myService.transfer(TestModel.MODEL_CVV_INCORRECT));

    }
}
