package com.example.microservice_small_square.domain.spi;

import com.example.microservice_small_square.domain.model.Restaurant;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);

}
