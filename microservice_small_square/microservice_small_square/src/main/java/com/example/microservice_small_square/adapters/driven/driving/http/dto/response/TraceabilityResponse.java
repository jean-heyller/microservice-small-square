package com.example.microservice_small_square.adapters.driven.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDate;
@AllArgsConstructor
@Getter
public class TraceabilityResponse {

    private  String idOrder;


    private final String idClient;

    private  String emailClient;


    private final LocalDate date;

    private String statusBefore;



    private  String statusAfter;

    private final String idEmployee;

    private  String emailEmployee;




    private String status;
}
