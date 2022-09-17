package com.localsearch.controller;

import com.localsearch.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleException(final BusinessException exception) {
        return ResponseEntity.status(500).body(exception.getMessage());
    }
}
