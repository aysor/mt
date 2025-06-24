package ru.netology.moneytransferservice.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.netology.moneytransferservice.model.exceptions.ConfirmationException;
import ru.netology.moneytransferservice.model.exceptions.ErrorResponse;
import ru.netology.moneytransferservice.model.exceptions.InputDataException;
import ru.netology.moneytransferservice.model.exceptions.TransferException;

@RestControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(InputDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse errorInputDataHandler(InputDataException e){
        System.out.printf("Myy Exception: %s in operation #%s\n", e.getMessage(), e.getId());
        return new ErrorResponse(e.getId(), e.getMessage());
    }

    @ExceptionHandler(TransferException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse errorTransfer(TransferException e){
        System.out.printf("Myy Exception: %s in operation #%s\n", e.getMessage(), e.getId());
        return new ErrorResponse(e.getId(), e.getMessage());
    }

    @ExceptionHandler(ConfirmationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse errorConfirm(ConfirmationException e){
        System.out.printf("Myy Exception: %s in operation #%s\n", e.getMessage(), e.getId());
        return new ErrorResponse(e.getId(), e.getMessage());
    }

}
