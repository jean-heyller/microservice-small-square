package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddSmsSenderRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.ISmsSenderRequestMapper;
import com.example.microservice_small_square.domain.api.ISmsSenderServicePort;
import com.example.microservice_small_square.domain.model.SmsSender;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

class SmsSenderControllerAdapterTest {


    @Mock
    private ISmsSenderServicePort smsSenderServicePort;

    @Mock
    private ISmsSenderRequestMapper smsSenderRequestMapper;

    @InjectMocks
    private SmsSenderControllerAdapter smsSenderControllerAdapter;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(smsSenderControllerAdapter).build();
    }

    @Test
     void testSendSms() throws Exception {
        AddSmsSenderRequest addSmsSenderRequest = new AddSmsSenderRequest("+571234567890", "Test message");
        SmsSender smsSender = new SmsSender("+571234567890", "Test message");
        when(smsSenderRequestMapper.toSmsSender(any(AddSmsSenderRequest.class))).thenReturn(smsSender);

        mockMvc.perform(post("/sms-sender/send")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(addSmsSenderRequest)))
                .andExpect(status().isOk());

        verify(smsSenderServicePort).sendSms(any(SmsSender.class));
    }

}