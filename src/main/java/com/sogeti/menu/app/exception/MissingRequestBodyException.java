package com.sogeti.menu.app.exception;

public class MissingRequestBodyException extends RuntimeException {
    public MissingRequestBodyException(String message) {
        super(message);
    }
}

