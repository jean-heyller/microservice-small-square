package com.example.microservice_small_square.adapters.driven.driving.http.dto.response;




import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Getter
public class OrderResponse {
    private final Long id;

    private final LocalDate date;


    private final Long idChef;

    private List<DishResponse> dishes;


    private final Long idClient;


    private final Long idRestaurant;
}
