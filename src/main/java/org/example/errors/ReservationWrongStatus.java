package org.example.errors;

import org.example.enums.ReservationStatus;

public class ReservationWrongStatus extends RuntimeException {

    private final ReservationStatus expectedStatus;
    private final ReservationStatus operationStatus;

    public ReservationWrongStatus(String message, ReservationStatus expectedStatus, ReservationStatus operationStatus) {
        super(message);
        this.expectedStatus = expectedStatus;
        this.operationStatus = operationStatus;
    }

    public ReservationStatus getExpectedStatus() {
        return expectedStatus;
    }

    public ReservationStatus getOperationStatus() {
        return operationStatus;
    }
}
