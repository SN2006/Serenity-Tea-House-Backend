package com.example.backend.enums;

public enum OrderStatus {
    IN_PROCESSING ("IN_PROCESSING"),
    PAID ("PAID");

    private String status;
    OrderStatus(String status) {
        this.status = status;
    }
}
