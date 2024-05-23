package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.personalized.AddDishType;
import com.example.microservice_small_square.adapters.driven.driving.http.util.AdapterConstans;
import com.example.microservice_small_square.domain.model.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class AddOrderRequest {

    @NotNull(message = AdapterConstans.FIELD_RESTAURANT_ID_NULL_MESSAGE)
    private Long id;

    @FutureOrPresent(message = AdapterConstans.FIELD_DATE_FUTURE_MESSAGE)
    private LocalDate date;

    @NotNull(message = AdapterConstans.FIELD_CHEF_ID_NULL_MESSAGE)
    private Long idChef;

    @NotNull(message = AdapterConstans.FIELD_DISHES_NULL_MESSAGE)
    @Size(min = 1)
    List<AddDishType> dishes;

    @NotNull(message = AdapterConstans.FIELD_DISHES_NULL_MESSAGE)
    private Long idRestaurant;

    @NotNull(message = AdapterConstans.FIELD_CLIENT_ID_NULL_MESSAGE)
    private Long idClient;



}
