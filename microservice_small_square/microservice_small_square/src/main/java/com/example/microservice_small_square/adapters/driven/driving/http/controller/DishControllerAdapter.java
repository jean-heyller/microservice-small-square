package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.IDishRequestMapper;
import com.example.microservice_small_square.domain.api.IDishServicePort;
import com.example.microservice_small_square.domain.model.Dish;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class DishControllerAdapter {
    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;

    public DishControllerAdapter(IDishServicePort dishServicePort, IDishRequestMapper dishRequestMapper) {
        this.dishServicePort = dishServicePort;
        this.dishRequestMapper = dishRequestMapper;
    }

    @PostMapping("/")
    public ResponseEntity<Void> addDish(@RequestBody AddDishRequest addDishRequest) {
        Dish dish = dishRequestMapper.addRequestToDish(addDishRequest);
        dishServicePort.saveDish(dish);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
}
