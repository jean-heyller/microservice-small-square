package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.model.SmsSender;
import com.example.microservice_small_square.domain.spi.ISmsSenderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import static org.mockito.Mockito.verify;

class SmsSenderUseCaseTest {

    @Mock
    private ISmsSenderService smsSenderService;

    private SmsSenderUseCase smsSenderUseCase;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        smsSenderUseCase = new SmsSenderUseCase(smsSenderService);
    }

    @Test
    public void testSendSms() {
        SmsSender smsSender = new SmsSender("1234567890", "Test message");

        smsSenderUseCase.sendSms(smsSender);

        verify(smsSenderService).sendSms(smsSender);
    }

}