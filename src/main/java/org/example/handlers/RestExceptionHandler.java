package org.example.handlers;

import org.example.errors.NotEnoughMoney;
import org.example.errors.OperationDoesntExist;
import org.example.errors.ReservationWrongStatus;
import org.example.responses.NotEnoughMoneyResponse;
import org.example.responses.OperationDoesntExistResponse;
import org.example.responses.ReservationWrongStatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(OperationDoesntExist.class)
    public ResponseEntity<OperationDoesntExistResponse> handleOperationDoesntExistResponse(OperationDoesntExist ex) {

        OperationDoesntExistResponse errorResponse = new OperationDoesntExistResponse(
                ex.getMessage(),
                ex.getOperationId()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(NotEnoughMoney.class)
    public ResponseEntity<NotEnoughMoneyResponse> handleNotEnoughMoneyResponseResponse(NotEnoughMoney ex) {

        NotEnoughMoneyResponse errorResponse = new NotEnoughMoneyResponse(
                ex.getMessage(),
                ex.getCurrentBalance()
        );

        return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(errorResponse);
    }

    @ExceptionHandler(ReservationWrongStatus.class)
    public ResponseEntity<ReservationWrongStatusResponse> handleNReservationWrongStatusResponse(ReservationWrongStatus ex) {

        ReservationWrongStatusResponse errorResponse = new ReservationWrongStatusResponse(
                ex.getMessage(),
                ex.getExpectedStatus(),
                ex.getOperationStatus()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> allOthersExceptionHandler(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Произошла ошибка на сервере");
    }

}
