package com.example.microservice_small_square.adapters.driven.driving.http.dto.personalized;

import com.example.microservice_small_square.adapters.driven.driving.http.util.AdapterConstans;

import com.example.microservice_small_square.adapters.driven.driving.http.util.dish.DishConstans;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
public class AddDishType {
    @NotNull(message = DishConstans.FIELD_DISH_ID_NULL_MESSAGE)
    private Long idDish;

    @NotNull(message = AdapterConstans.FIELD_QUANTITY_NULL_MESSAGE)
    @Min(value = 1, message = AdapterConstans.FIELD_QUANTITY_SIZE_MESSAGE)
    private final Integer quantity;

}
