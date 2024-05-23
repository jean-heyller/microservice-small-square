package com.example.microservice_small_square.domain.api;

import com.example.microservice_small_square.domain.model.Restaurant;



public interface IRestaurantServicePort {
    void saveRestaurant(Restaurant restaurant);


}
