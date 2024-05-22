package com.example.microservice_small_square.adapters.driven.jpa.mysql.repository;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {

        Optional<DishEntity> findByRestaurantIdAndName( Long restaurantId, String name);
}
