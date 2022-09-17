package com.localsearch.service.impl;

import com.localsearch.exception.BusinessException;
import com.localsearch.feign.CodingSessionClient;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class BusinessServiceImplTest {

    @Spy
    @InjectMocks
    private BusinessServiceImpl businessService;

    @Mock
    private CodingSessionClient codingSessionClient;

    @Test
    void should_throw_exception_when_request_to_coding_session_client_throw_feign_exception() {
        doThrow(FeignException.class).when(codingSessionClient).retrieveCodingSessionInfo(anyString());
        assertThrows(BusinessException.class, () -> businessService.getBusinessDetails("1"));
    }
}