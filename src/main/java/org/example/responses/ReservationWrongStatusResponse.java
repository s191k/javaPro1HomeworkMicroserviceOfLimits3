package org.example.responses;

import lombok.Getter;
import org.example.enums.ReservationStatus;

@Getter
public class ReservationWrongStatusResponse {

    private final String message;
    private final ReservationStatus expectedStatus;
    private final ReservationStatus operationStatus;

    public ReservationWrongStatusResponse(String message, ReservationStatus expectedStatus, ReservationStatus operationStatus) {
        this.message = message;
        this.expectedStatus = expectedStatus;
        this.operationStatus = operationStatus;
    }
}
