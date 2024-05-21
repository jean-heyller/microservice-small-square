package com.example.microservice_small_square.domain.api;

import com.example.microservice_small_square.domain.model.Dish;

public interface IDishServicePort {
    void saveDish(Dish dish);
}
