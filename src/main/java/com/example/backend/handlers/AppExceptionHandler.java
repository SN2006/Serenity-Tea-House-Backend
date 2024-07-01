package com.example.backend.handlers;

import com.example.backend.dto.userDtos.ErrorDto;
import com.example.backend.exceptions.AuthException;
import com.example.backend.exceptions.OrderException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(OrderException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(OrderException ex) {
        return ResponseEntity.status(ex.getCode())
                .body(new ErrorDto(ex.getMessage()));
    }

}
