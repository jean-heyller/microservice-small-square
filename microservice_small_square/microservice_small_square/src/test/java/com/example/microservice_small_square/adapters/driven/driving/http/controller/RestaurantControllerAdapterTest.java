package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddRestaurantRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.RestaurantResponse;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.IRestaurantRequestMapper;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.response.IRestaurantResponseMapper;
import com.example.microservice_small_square.domain.api.IRestaurantServicePort;

import com.example.microservice_small_square.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestaurantControllerAdapterTest {
    private IRestaurantRequestMapper mapper;
    private IRestaurantServicePort service;
    private RestaurantControllerAdapter controller;

    private IRestaurantResponseMapper responseMapper;


    @BeforeEach
    public void setUp() {
        mapper = Mockito.mock(IRestaurantRequestMapper.class);
        service = Mockito.mock(IRestaurantServicePort.class);
        controller = new RestaurantControllerAdapter(mapper, service,responseMapper);
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

    @Test
    void getAllRestaurants() {
        // Arrange
        IRestaurantServicePort service = Mockito.mock(IRestaurantServicePort.class);
        IRestaurantResponseMapper responseMapper = Mockito.mock(IRestaurantResponseMapper.class);
        RestaurantControllerAdapter controller = new RestaurantControllerAdapter(null, service, responseMapper);

        List<Restaurant> testRestaurants = List.of(
                new Restaurant(1L, "Test Name 1", "Test Nit 1", "Test Address 1", "Test Phone 1", "Test URL 1", 1L),
                new Restaurant(2L, "Test Name 2", "Test Nit 2", "Test Address 2", "Test Phone 2", "Test URL 2", 2L)
        );
        List<RestaurantResponse> testResponses = testRestaurants.stream()
                .map(r -> new RestaurantResponse(r.getUrlLogo(), r.getName()))
                .collect(Collectors.toList());

        when(service.getAllRestaurants(0, 10)).thenReturn(testRestaurants);
        when(responseMapper.toRestaurantResponseList(testRestaurants)).thenReturn(testResponses);


        ResponseEntity<List<RestaurantResponse>> result = controller.getAllRestaurants(0, 10);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(testResponses, result.getBody());
        verify(service, times(1)).getAllRestaurants(0, 10);
        verify(responseMapper, times(1)).toRestaurantResponseList(testRestaurants);
    }
}




