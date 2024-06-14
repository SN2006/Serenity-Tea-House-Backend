package com.example.backend.exceptions;

import org.springframework.http.HttpStatus;

public class OrderException extends RuntimeException {
    private final HttpStatus code;

    public OrderException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }

    public HttpStatus getCode() {
        return code;
    }
}
