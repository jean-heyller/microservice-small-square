package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantRepository;
import com.example.microservice_small_square.domain.model.Restaurant;
import com.example.microservice_small_square.domain.spi.IRestaurantPersistencePort;

public class RestaurantAdapter implements IRestaurantPersistencePort {
    private final IRestaurantRepository restaurantRepository;

    public RestaurantAdapter(IRestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurant);
    }
}
