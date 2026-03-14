package org.example;

import exceptions.AppException;
import exceptions.ArchiveOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class StandardOrderProcessorTest {

    private OrderRepository repository;
    private PaymentMethod cardPayment;
    private PaymentMethod payPalPayment;
    private StandardOrderProcessor processor;
    private Email validEmail;

    @BeforeEach
    void setUp() {
        repository = new InMemoryOrderRepository();
        cardPayment = new CardPayment();
        payPalPayment = new PayPalPayment();
        processor = new StandardOrderProcessor(cardPayment, repository);
        validEmail = new Email("test@ukma.edu.ua");
    }

    @Test
    void testSuccessfulOrderProcessing() {
        OrderItem item = new OrderItem("Laptop", new Money(new BigDecimal("25000"), "UAH"), "ELECTRONICS");
        Order order = new Order(validEmail, new OrderItem[]{item});

        assertDoesNotThrow(() -> processor.process(order));
        assertEquals(OrderStatus.ARCHIVED, order.getStatus());
        assertTrue(repository.findById(order.getId()).isPresent());
    }

    @Test
    void testClearanceDiscountApplied() {
        OrderItem item = new OrderItem("Old Shirt", new Money(new BigDecimal("1000"), "UAH"), "CLEARANCE");
        Order order = new Order(validEmail, new OrderItem[]{item});

        assertDoesNotThrow(() -> processor.process(order));
        assertEquals(OrderStatus.ARCHIVED, order.getStatus());
    }

    @Test
    void testRuleR9ViolationThrowsAppException() {
        OrderItem item = new OrderItem("Expensive TV", new Money(new BigDecimal("50001"), "UAH"), "ELECTRONICS");
        Order order = new Order(validEmail, new OrderItem[]{item});

        AppException exception = assertThrows(AppException.class, () -> processor.process(order));
        assertTrue(exception.getMessage().contains("50 000"));
    }


    @Test
    void testPayPalPaymentBelowMinimum() {
        StandardOrderProcessor paypalProcessor = new StandardOrderProcessor(payPalPayment, repository);
        OrderItem item = new OrderItem("Cheap Cable", new Money(new BigDecimal("399"), "UAH"), "ELECTRONICS");
        Order order = new Order(validEmail, new OrderItem[]{item});

        AppException exception = assertThrows(AppException.class, () -> paypalProcessor.process(order));
        assertTrue(exception.getMessage().contains("400"));
    }

    @Test
    void testArchiveOperationException() {
        Order order = new Order(validEmail, new OrderItem[0]);
        assertThrows(ArchiveOperationException.class, () -> processor.archiveAfterDelivery(order));
    }

    @Test
    void testInvalidEmailThrowsException() {
        assertThrows(AppException.class, () -> new Email("invalid-email.com"));
    }
}