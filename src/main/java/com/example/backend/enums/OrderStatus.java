package com.example.backend.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    NEW("NEW"),
    IN_PROCESSING ("IN_PROCESSING"),
    COMPLETED ("COMPLETED"),;

    private final String status;
    OrderStatus(String status) {
        this.status = status;
    }
}
