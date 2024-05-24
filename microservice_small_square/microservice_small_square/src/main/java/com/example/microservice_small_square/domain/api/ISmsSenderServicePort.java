package com.example.microservice_small_square.domain.api;


import com.example.microservice_small_square.domain.model.SmsSender;

public interface ISmsSenderServicePort {
    void sendSms(SmsSender smsSender);


}
