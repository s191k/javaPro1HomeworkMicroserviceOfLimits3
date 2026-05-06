package org.example.errors;

import lombok.Getter;
import org.example.enums.ReservationStatus;

@Getter
public class ReservationWrongStatus extends RuntimeException {

    private final ReservationStatus expectedStatus;
    private final ReservationStatus operationStatus;

    public ReservationWrongStatus(String message, ReservationStatus expectedStatus, ReservationStatus operationStatus) {
        super(message);
        this.expectedStatus = expectedStatus;
        this.operationStatus = operationStatus;
    }

}
