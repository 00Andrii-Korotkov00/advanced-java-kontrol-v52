package org.example;

import exceptions.AppException;

import java.math.BigDecimal;

public class CardPayment implements PaymentMethod {
    private static final BigDecimal MAX_LIMIT = new BigDecimal("35000");

    @Override
    public void processPayment(Money amount) {
        if (amount.getAmount().compareTo(MAX_LIMIT) > 0) {
            throw new AppException("Card payment exceeds maximum limit of 35000");
        }
        System.out.println("Processing card payment for: " + amount);
    }
}
