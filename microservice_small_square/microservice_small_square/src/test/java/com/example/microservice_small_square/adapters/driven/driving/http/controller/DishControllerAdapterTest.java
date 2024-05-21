package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.IDishRequestMapper;
import com.example.microservice_small_square.domain.api.IDishServicePort;
import com.example.microservice_small_square.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

 class DishControllerAdapterTest {

    private IDishServicePort dishServicePort;
    private IDishRequestMapper dishRequestMapper;
    private DishControllerAdapter dishControllerAdapter;

    @BeforeEach
    public void setup() {
        dishServicePort = Mockito.mock(IDishServicePort.class);
        dishRequestMapper = Mockito.mock(IDishRequestMapper.class);
        dishControllerAdapter = new DishControllerAdapter(dishServicePort, dishRequestMapper);
    }

    @Test
     void testAddDish() {
        AddDishRequest request = new AddDishRequest("Test Dish", "Test Description", 10.0f, "Test URL", "Test Category", 1L);
        Dish dish = new Dish("Test Dish", 10.0, "Test Description", "Test URL", "Test Category", null, 1L);

        Mockito.when(dishRequestMapper.addRequestToDish(request)).thenReturn(dish);

        ResponseEntity<Void> response = dishControllerAdapter.addDish(request);

        verify(dishServicePort).saveDish(dish);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}