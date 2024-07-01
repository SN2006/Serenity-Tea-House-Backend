package com.example.backend.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OrderException extends RuntimeException {
    private final HttpStatus code;

    public OrderException(String message, HttpStatus code) {
        super(message);
        this.code = code;
    }

}
