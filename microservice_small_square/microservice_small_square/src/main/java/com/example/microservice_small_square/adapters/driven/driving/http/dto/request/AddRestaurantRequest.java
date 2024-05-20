package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import com.example.microservice_small_square.adapters.driven.driving.http.util.AdapterConstans;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class AddRestaurantRequest {
    @NotBlank(message = AdapterConstans.FIELD_NAME_NULL_MESSAGE)
    @Size(max = 50, message = AdapterConstans.FIELD_NAME_SIZE_MESSAGE)
    @Pattern(regexp = ".*[a-zA-Z]+.*", message = AdapterConstans.FIELD_NAME_PATTERN_MESSAGE)
    private final String name;

    @NotBlank(message = AdapterConstans.FIELD_NIT_NULL_MESSAGE)
    @Pattern(regexp = "\\d+", message = AdapterConstans.FIELD_NIT_PATTERN_MESSAGE)
    private final String nit;

    @NotBlank(message = AdapterConstans.FIELD_ADDRESS_NULL_MESSAGE)
    @Size(max = 100, message = AdapterConstans.FIELD_ADDRESS_SIZE_MESSAGE)
    private final String address;

    @NotBlank(message = AdapterConstans.FIELD_PHONE_NUMBER_NULL_MESSAGE)
    @Pattern(regexp = "\\+?\\d{1,13}", message = AdapterConstans.FIELD_PHONE_NUMBER_PATTERN_MESSAGE)
    private final String phoneNumber;

    @NotBlank(message = AdapterConstans.FIELD_URL_LOGO_NULL_MESSAGE)
    private final String urlLogo;

    @NotBlank(message = AdapterConstans.FIELD_OWNER_ID_NULL_MESSAGE)
    private final String ownerId;
}