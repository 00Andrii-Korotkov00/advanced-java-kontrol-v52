package org.example;

import exceptions.AppException;
import exceptions.ArchiveOperationException;

import java.math.BigDecimal;

public class StandardOrderProcessor extends OrderProcessorTemplate {
    private final PaymentMethod paymentMethod;
    private final OrderRepository orderRepository;

    public StandardOrderProcessor(PaymentMethod paymentMethod, OrderRepository orderRepository) {
        this.paymentMethod = paymentMethod;
        this.orderRepository = orderRepository;
    }

    @Override
    protected void validate(Order order) {
        BigDecimal limit = new BigDecimal("50000");
        for (OrderItem item : order.getItems()) {
            if (item.getPrice().getAmount().compareTo(limit) > 0) {
                throw new AppException("Помилка: Позиція перевищує ліміт у 50 000");
            }
        }
    }

    @Override
    protected void calculate(Order order) {
        System.out.println("Виконується попередній розрахунок знижок для замовлення...");
    }

    @Override
    protected void pay(Order order) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        String currency = "UAH";

        for (OrderItem item : order.getItems()) {
            BigDecimal price = item.getPrice().getAmount();
            if ("CLEARANCE".equalsIgnoreCase(item.getCategory())) {
                price = price.multiply(new BigDecimal("0.80"));
            }
            totalAmount = totalAmount.add(price);
            if (currency.equals("UAH")) currency = item.getPrice().getCurrency();
        }

        if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
            paymentMethod.processPayment(new Money(totalAmount, currency));
        }

        order.setStatus(OrderStatus.PAID);
    }

    @Override
    protected void complete(Order order) {
        order.setStatus(OrderStatus.SHIPPED);
        order.setStatus(OrderStatus.DELIVERED);
    }

    @Override
    protected void archiveAfterDelivery(Order order) {
        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new ArchiveOperationException("Неможливо архівувати. Поточний статус: " + order.getStatus());
        }

        order.setStatus(OrderStatus.ARCHIVED);
        orderRepository.save(order);
    }
}