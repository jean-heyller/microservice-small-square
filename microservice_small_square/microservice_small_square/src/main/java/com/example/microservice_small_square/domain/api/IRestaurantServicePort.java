package com.example.microservice_small_square.domain.api;

import com.example.microservice_small_square.domain.model.Restaurant;

import java.util.List;


public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);

    public List<Restaurant> getAllRestaurants(Integer page, Integer size);


}
