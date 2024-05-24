package com.example.microservice_small_square.adapters.driven.jpa.mysql.repository;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByIdRestaurantAndStatus(Long idRestaurant, String status);

    Page<OrderEntity> findAll(Pageable pageable);


    Page<OrderEntity> findByIdRestaurantAndStatusAndIdClient(Long idRestaurant, String status,Long idClient, Pageable pageable);

    List<OrderEntity> findByIdClientAndStatusIn(Long idClient, List<String> statuses);



}
