package com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper;

import com.example.microservice_small_square.domain.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestaurantRepository extends JpaRepository<Restaurant, Long> {
    Restaurant findByName(String name);

}
