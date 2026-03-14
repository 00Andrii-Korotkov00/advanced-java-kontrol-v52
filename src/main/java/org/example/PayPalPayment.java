package org.example;

import exceptions.AppException;

import java.math.BigDecimal;

public class PayPalPayment implements PaymentMethod {
    private static final BigDecimal MIN_LIMIT = new BigDecimal("400");

    @Override
    public void processPayment(Money amount) {
        if (amount.getAmount().compareTo(MIN_LIMIT) < 0) {
            throw new AppException("PayPal payment is below minimum limit of 400");
        }
        System.out.println("Processing PayPal payment for: " + amount);
    }
}
