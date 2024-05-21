package com.example.microservice_small_square.domain.api;

import com.example.microservice_small_square.domain.model.Dish;

import java.util.Optional;

public interface IDishServicePort {
    void saveDish(Dish dish);
    void updateDish(Long id, Optional<Double> price, Optional<String> description);
}
