package com.example.microservice_small_square.adapters.driven.client;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddSmsSenderRequest;
import com.example.microservice_small_square.configuration.client.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "sms-service", url = "http://localhost:8092", configuration = FeignClientConfig.class)
public interface SmsClient {
    @PostMapping("sms-sender/send")
    void sendSms(@RequestBody AddSmsSenderRequest smsRequest);

    @GetMapping(value = "sms-sender/message", consumes = MediaType.APPLICATION_JSON_VALUE)
    String getMessage(@RequestParam("number") String number);
}