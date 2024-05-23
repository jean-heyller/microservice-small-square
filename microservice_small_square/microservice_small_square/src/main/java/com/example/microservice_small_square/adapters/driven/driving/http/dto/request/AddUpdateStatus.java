package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Getter
public class AddUpdateStatus {
    @NotBlank
    private final Boolean status;

    @NotBlank
    private final Long restaurantId;
}
