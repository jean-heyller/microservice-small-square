package com.example.microservice_small_square.domain.spi;

import com.example.microservice_small_square.domain.model.Dish;

import java.util.Optional;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
    void updateDish(Long id, Optional<Double> price, Optional<String> description);
}
