package com.example.microservice_small_square.adapters.driven.mongodb.entity;

import lombok.Data;

import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "traceability")
@Data
public class TraceabilityEntity {

    @Id
    private String id;

    private String idOrder;

    private String idClient;

    private String emailClient;

    private LocalDateTime date = LocalDateTime.now();

    private String status;

    private String statusBefore;

    private String statusAfter;

    private String idEmployee;

    private String emailEmployee;


}
