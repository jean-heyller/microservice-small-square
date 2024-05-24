package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddSmsSenderRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.ISmsSenderRequestMapper;
import com.example.microservice_small_square.domain.api.ISmsSenderServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sms-sender")
@RequiredArgsConstructor
@Validated
public class SmsSenderControllerAdapter {

    private final ISmsSenderServicePort smsSenderServicePort;

    private final ISmsSenderRequestMapper smsSenderRequestMapper;

    @PostMapping("/send")
    public ResponseEntity<Void> sendSms(@RequestBody AddSmsSenderRequest addSmsSenderRequest) {
        smsSenderServicePort.sendSms(smsSenderRequestMapper.toSmsSender(addSmsSenderRequest));
        return ResponseEntity.ok().build();
    }
}
