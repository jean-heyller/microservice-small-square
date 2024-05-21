package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.api.IDishServicePort;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.spi.IDishPersistencePort;

public class DishUseCase implements IDishServicePort {

    private final IDishPersistencePort dishPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public void saveDish(Dish dish) {
        if (dish.getPrice() > 0) {
            dishPersistencePort.saveDish(dish);
        } else {
            throw new IllegalArgumentException("El precio del plato debe ser mayor a 0");
        }
    }
}
