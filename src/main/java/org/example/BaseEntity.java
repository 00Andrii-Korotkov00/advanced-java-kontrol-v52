package org.example;

public abstract class BaseEntity {
    private final String id;

    public BaseEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
