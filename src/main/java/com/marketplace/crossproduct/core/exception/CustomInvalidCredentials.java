package com.marketplace.crossproduct.core.exception;

public class CustomInvalidCredentials extends RuntimeException {
    public CustomInvalidCredentials(String message) {
        super(message);
    }
}
