package org.example;

import exceptions.AppException;

public class Email {
    private final String address;

    public Email(String address) {
        if (address == null || !address.contains("@")) {
            throw new AppException("Invalid email address format");
        }
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return address;
    }
}