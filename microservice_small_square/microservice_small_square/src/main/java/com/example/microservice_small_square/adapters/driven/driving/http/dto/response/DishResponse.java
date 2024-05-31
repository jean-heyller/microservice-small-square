package com.example.microservice_small_square.adapters.driven.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DishResponse {

    private Long id;
    private String name;
    private double price;
    private String description;
    private String imageUrl;
    private String category;
}
