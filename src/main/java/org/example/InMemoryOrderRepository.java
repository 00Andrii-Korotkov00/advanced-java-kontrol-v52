package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> database = new HashMap<>();

    @Override
    public Optional<Order> findById(String id) {
        return Optional.ofNullable(database.get(id));
    }

    @Override
    public void save(Order order) {
        database.put(order.getId(), order);
    }
}