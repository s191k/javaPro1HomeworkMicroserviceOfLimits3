package org.example.errors;

import java.math.BigDecimal;

public class NotEnoughMoney extends RuntimeException {

    private final BigDecimal currentBalance;

    public NotEnoughMoney(String message, BigDecimal currentBalance) {
        super(message);
        this.currentBalance = currentBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

}
