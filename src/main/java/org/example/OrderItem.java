package org.example;

public class OrderItem {
    private final String name;
    private final Money price;
    private final String category;

    public OrderItem(String name, Money price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}
