package com.example.microservice_small_square.domain.spi;

import com.example.microservice_small_square.domain.model.Dish;

public interface IDishPersistencePort {
    void saveDish(Dish dish);
}
