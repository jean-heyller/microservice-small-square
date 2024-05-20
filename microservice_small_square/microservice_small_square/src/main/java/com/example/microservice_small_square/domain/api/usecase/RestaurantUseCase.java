package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.api.IRestaurantServicePort;
import com.example.microservice_small_square.domain.model.Restaurant;
import com.example.microservice_small_square.domain.spi.IRestaurantPersistencePort;

public class RestaurantUseCase implements IRestaurantServicePort {

    private final IRestaurantPersistencePort persistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
         persistencePort.saveRestaurant(restaurant);
    }

}

