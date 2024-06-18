package com.example.microservice_small_square.adapters.driven.mongodb.proyections;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class EmployeeAverageTime {
    private String idEmployee;
    private Double averageProcessingTime;
}
