package com.localsearch.controller;

import com.localsearch.exception.BusinessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExceptionControllerTest {

    @Spy
    private ExceptionController exceptionController;

    @Test
    public void should_return_500_error_when_business_exception_is_thrown() {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exceptionController.handleException(new BusinessException("")).getStatusCode());
    }
}