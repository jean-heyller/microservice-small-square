package com.example.microservice_small_square.adapters.driven.jpa.mysql.repository;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
    Optional<RestaurantEntity> findById(Long id);

    Page<RestaurantEntity> findAll(Pageable pageable);
}
