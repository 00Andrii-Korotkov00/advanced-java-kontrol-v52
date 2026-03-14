package org.example;

import java.util.Arrays;
import java.util.UUID;

public class Order extends BaseEntity {
    private final Email customerEmail;
    private OrderItem[] items;
    private OrderStatus status;

    public Order(Email customerEmail, OrderItem[] items) {
        this(UUID.randomUUID().toString(), customerEmail, items);
    }

    public Order(String id, Email customerEmail, OrderItem[] items) {
        super(id);
        this.customerEmail = customerEmail;
        this.status = OrderStatus.NEW;

        if (items != null) {
            this.items = Arrays.copyOf(items, items.length);
        } else {
            this.items = new OrderItem[0];
        }
    }

    public Email getCustomerEmail() {
        return customerEmail;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public OrderItem[] getItems() {
        return Arrays.copyOf(this.items, this.items.length);
    }
}
