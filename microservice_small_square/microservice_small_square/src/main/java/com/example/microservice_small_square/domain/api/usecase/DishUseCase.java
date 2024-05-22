package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.api.IDishServicePort;
import com.example.microservice_small_square.domain.exception.SizeMinException;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.spi.IDishPersistencePort;

import java.util.Optional;
import java.util.OptionalDouble;

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
            throw new SizeMinException();
        }
    }

    @Override
    public void updateDish(Long id, Optional<Double> price, Optional<String> description,
                           Long restaurantId) {
        dishPersistencePort.updateDish(id, price, description, restaurantId);
    }

    @Override
    public void changeStatus(Long id, Long restaurantId) {
        dishPersistencePort.changeStatus(id, restaurantId);
    }
}
