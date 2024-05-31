package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import com.example.microservice_small_square.adapters.driven.driving.http.util.AdapterConstans;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class AddOrderUpdateRequest {

    @NotNull(message = AdapterConstans.FIELD_RESTAURANT_ID_NULL_MESSAGE)
    private Long idRestaurant;

    @NotNull(message = AdapterConstans.FIELD_CLIENT_ID_NULL_MESSAGE)
    private Long idClient;

    @NotNull(message = AdapterConstans.FIELD_ID_NULL_MESSAGE)
    private Long id;

    @NotNull(message = AdapterConstans.FIELD_CHEF_ID_NULL_MESSAGE)
    private Long idChef;
}
