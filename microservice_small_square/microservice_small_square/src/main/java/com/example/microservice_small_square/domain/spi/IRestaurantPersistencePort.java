package com.example.microservice_small_square.domain.spi;

import com.example.microservice_small_square.domain.model.Restaurant;

import java.util.List;

public interface IRestaurantPersistencePort {

    void saveRestaurant(Restaurant restaurant);

    public List<Restaurant> getAllRestaurants(Integer page, Integer size);

}
