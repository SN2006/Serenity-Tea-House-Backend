package com.example.backend.enums;

import lombok.Getter;

@Getter
public enum MessageType {

    TEXT("TEXT");

    private final String value;

    MessageType(String value) {
        this.value = value;
    }

}
