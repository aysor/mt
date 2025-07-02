package ru.netology.moneytransferservice;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.moneytransferservice.model.ConfirmModel;
import ru.netology.moneytransferservice.model.MoneyTransferModel;
import ru.netology.moneytransferservice.model.MoneyTransferServiceResponse;
import ru.netology.moneytransferservice.testModel.TestModel;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneyTransferServiceApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;
    private static final String HOST = "http://localhost:";
    private static final int PORT = 5500;
    public static final int OPERATION_ID = 1;
    private static final String CODE = "0000";
    private static Integer portRandom;
    public static final MoneyTransferModel TRANSFER_REQUEST = TestModel.MODEL_OK;
    public static final ConfirmModel CONFIRM_OPERATION_REQUEST = new ConfirmModel(OPERATION_ID, CODE);

    @Container
    private final static GenericContainer<?> container = new GenericContainer<>("moneytransferapp:4.0").
            withExposedPorts(PORT);

    @BeforeAll
    public static void setUp() {
        portRandom = container.getMappedPort(PORT);
    }

    @DisplayName("Test for '/transfer'")
    @Test
    void contextLoadsTestOne(){
        ResponseEntity<MoneyTransferServiceResponse> forTransfer = restTemplate.postForEntity(HOST + portRandom +
                TestModel.ENDPOINT_TRANSFER, TRANSFER_REQUEST, MoneyTransferServiceResponse.class);
        MoneyTransferServiceResponse expected = new MoneyTransferServiceResponse(1);
        Assertions.assertEquals(forTransfer.getBody(), expected);
    }

    @DisplayName("Test for '/confirmOperation'")
    @Test
    void contextLoadsConfirmOperation(){
        ResponseEntity<MoneyTransferServiceResponse> forConfirmOperation = restTemplate.postForEntity(HOST + portRandom +
                TestModel.ENDPOINT_CONFIRM, CONFIRM_OPERATION_REQUEST, MoneyTransferServiceResponse.class);
        MoneyTransferServiceResponse expected = new MoneyTransferServiceResponse(1);
        Assertions.assertEquals(forConfirmOperation.getBody(), expected);
    }
}