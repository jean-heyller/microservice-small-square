package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class AddTraceabilityRequest {

    @NotNull(message = "Id cannot be null")
    private final Long id;

    @NotBlank(message = "Client Id cannot be blank")
    private final String idClient;

    @Email(message = "Email should be valid")
    private final String emailClient;

    @PastOrPresent(message = "Date should be in the past or present")
    private final LocalDate date;
    @NotBlank(message = "Status Before cannot be blank")
    private String statusBefore;

    @NotBlank(message = "Status After cannot be blank")
    private final String statusAfter;

    @NotNull(message = "Employee Id cannot be null")
    private final Long idEmployee;

    @Email(message = "Employee Email should be valid")
    private final String emailEmployee;
}
