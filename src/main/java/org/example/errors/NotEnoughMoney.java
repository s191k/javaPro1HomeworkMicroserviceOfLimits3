package org.example.errors;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class NotEnoughMoney extends RuntimeException {

    private final BigDecimal currentBalance;

    public NotEnoughMoney(String message, BigDecimal currentBalance) {
        super(message);
        this.currentBalance = currentBalance;
    }

}
