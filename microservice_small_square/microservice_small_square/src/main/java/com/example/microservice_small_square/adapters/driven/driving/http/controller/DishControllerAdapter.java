package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.IDishRequestMapper;
import com.example.microservice_small_square.domain.api.IDishServicePort;
import com.example.microservice_small_square.domain.model.Dish;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dish")
@Validated
@RequiredArgsConstructor
public class DishControllerAdapter {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;

    @PostMapping("/")
     public ResponseEntity<Void> addDish(@RequestBody AddDishRequest request){
            dishServicePort.saveDish(dishRequestMapper.addRequestToDish(request));
            return ResponseEntity.status(HttpStatus.CREATED).build();
     }

}
