package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddRestaurantRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.IRestaurantRequestMapper;
import com.example.microservice_small_square.domain.api.IRestaurantServicePort;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rol")
@RequiredArgsConstructor
@Validated
public class RestaurantControllerAdapter {
    private final IRestaurantRequestMapper restaurantRequestMapper;

    private final IRestaurantServicePort restaurantServicePort;

    @PostMapping("/")
    public ResponseEntity<Void> addRestaurant(@Valid @RequestBody AddRestaurantRequest request){
        restaurantServicePort.saveRestaurant(restaurantRequestMapper.addRequestToRestaurant(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
