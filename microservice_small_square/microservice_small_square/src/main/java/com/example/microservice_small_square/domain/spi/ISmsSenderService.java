package com.example.microservice_small_square.domain.spi;

import com.example.microservice_small_square.domain.model.SmsSender;

public interface ISmsSenderService {
    void sendSms(SmsSender smsSender);
}