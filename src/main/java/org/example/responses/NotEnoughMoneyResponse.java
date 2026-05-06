package org.example.responses;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NotEnoughMoneyResponse {

    private final String message;
    private final BigDecimal availableAmount;

    public NotEnoughMoneyResponse(String message, BigDecimal availableAmount) {
        this.message = message;
        this.availableAmount = availableAmount;
    }
}
