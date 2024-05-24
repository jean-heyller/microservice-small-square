package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.api.ISmsSenderServicePort;
import com.example.microservice_small_square.domain.model.SmsSender;
import com.example.microservice_small_square.domain.spi.ISmsSenderService;

public class SmsSenderUseCase implements ISmsSenderServicePort {
    private final ISmsSenderService smsSenderService;

    public SmsSenderUseCase(ISmsSenderService smsSenderService) {
        this.smsSenderService = smsSenderService;
    }

    @Override
    public void sendSms(SmsSender smsSender) {
        smsSenderService.sendSms(smsSender);

    }
}
