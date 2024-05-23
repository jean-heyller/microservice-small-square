package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import com.example.microservice_small_square.adapters.driven.driving.http.util.AdapterConstans;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.*;


@AllArgsConstructor
@Getter
public class AddDishRequest {

    @NotBlank(message = AdapterConstans.FIELD_NAME_NULL_MESSAGE)
    @Size(max = 50, message = AdapterConstans.FIELD_NAME_SIZE_MESSAGE)
    @Pattern(regexp = ".*[a-zA-Z]+.*", message = AdapterConstans.FIELD_NAME_PATTERN_MESSAGE)
    private final String name;

    @NotBlank(message = AdapterConstans.FIELD_DESCRIPTION_NULL_MESSAGE)
    @Size(max = 100 , message = AdapterConstans.FIELD_DESCRIPTION_SIZE_MESSAGE)
    private final String description;

    @NotNull(message = AdapterConstans.FIELD_PRICE_NULL_MESSAGE)
    @DecimalMin(value = "0.0", inclusive = false, message = AdapterConstans.FIELD_PRICE_MIN_MESSAGE)
    private final Float price;


    @NotBlank(message = AdapterConstans.FIELD_IMAGE_URL_NULL_MESSAGE)
    private String imageUrl;

    @NotBlank(message = AdapterConstans.FIELD_CATEGORY_NULL_MESSAGE)
    private String category;

    @NotNull(message = AdapterConstans.FIELD_RESTAURANT_ID_NULL_MESSAGE)
    private final Long restaurantId;


}
