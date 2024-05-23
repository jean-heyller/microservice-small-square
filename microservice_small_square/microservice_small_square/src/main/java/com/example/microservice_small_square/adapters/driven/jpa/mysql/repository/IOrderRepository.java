package com.example.microservice_small_square.adapters.driven.jpa.mysql.repository;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByIdRestaurantAndStatus(Long idRestaurant, String status);

}
