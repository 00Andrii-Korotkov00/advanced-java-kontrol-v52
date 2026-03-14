package org.example;

import java.math.BigDecimal;

public class BankTransferPayment implements PaymentMethod {
    private static final BigDecimal COMMISSION_RATE = new BigDecimal("1.025");

    @Override
    public void processPayment(Money amount) {
        BigDecimal finalAmount = amount.getAmount().multiply(COMMISSION_RATE);
        Money moneyWithCommission = new Money(finalAmount, amount.getCurrency());
        System.out.println("Processing bank transfer payment for: " + moneyWithCommission + " (includes 2.5% commission)");
    }
}
