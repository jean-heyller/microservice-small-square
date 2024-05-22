package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddRestaurantRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.IRestaurantRequestMapper;
import com.example.microservice_small_square.domain.api.IRestaurantServicePort;

import com.example.microservice_small_square.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

 class RestaurantControllerAdapterTest {
    private IRestaurantRequestMapper mapper;
    private IRestaurantServicePort service;
    private RestaurantControllerAdapter controller;

    @BeforeEach
    public void setUp() {
        mapper = Mockito.mock(IRestaurantRequestMapper.class);
        service = Mockito.mock(IRestaurantServicePort.class);
        controller = new RestaurantControllerAdapter(mapper, service);
    }

    @Test
     void testAddRestaurant() {
        AddRestaurantRequest request = new AddRestaurantRequest("Test Name", "Test Nit", "Test Address", "Test Phone", "Test URL", 1L);
        Restaurant restaurant = new Restaurant(1L, "Test Name", "Test Nit", "Test Address", "Test Phone", "Test URL", 1L);
        when(mapper.addRequestToRestaurant(request)).thenReturn(restaurant);

        ResponseEntity<Void> response = controller.addRestaurant(request);

        verify(service).saveRestaurant(restaurant);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}