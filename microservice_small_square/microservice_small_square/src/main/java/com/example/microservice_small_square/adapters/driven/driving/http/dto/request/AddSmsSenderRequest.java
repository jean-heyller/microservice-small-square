package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddSmsSenderRequest {

    @NotBlank(message = "The field 'from' is mandatory")
    private  String to;
    @NotBlank(message = "The field 'message' is mandatory")
    private  String message;
}
