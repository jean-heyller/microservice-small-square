package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import com.example.microservice_small_square.adapters.driven.driving.http.util.AdapterConstans;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class AddDishUpdapteRequest {
    @NotBlank(message = AdapterConstans.FIELD_NAME_NULL_MESSAGE)
    private final Long id;

    @NotNull(message = AdapterConstans.FIELD_PRICE_NULL_MESSAGE)
    @DecimalMin(value = "0.0", inclusive = false, message = AdapterConstans.FIELD_PRICE_MIN_MESSAGE)
    private final Double price;

    @NotBlank(message = AdapterConstans.FIELD_DESCRIPTION_NULL_MESSAGE)
    @Size(max = 100, message = AdapterConstans.FIELD_DESCRIPTION_SIZE_MESSAGE)
    private final String description;

}
