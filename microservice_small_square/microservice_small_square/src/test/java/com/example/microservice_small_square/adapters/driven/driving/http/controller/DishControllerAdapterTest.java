package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishUpdapteRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.DishResponse;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.IDishRequestMapper;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.response.IDishResponseMapper;
import com.example.microservice_small_square.domain.api.IDishServicePort;
import com.example.microservice_small_square.domain.model.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DishControllerAdapterTest {

    private IDishServicePort dishServicePort;
    private IDishRequestMapper dishRequestMapper;
    private DishControllerAdapter dishControllerAdapter;

    private IDishResponseMapper dishResponseMapper;

    @BeforeEach
    public void setup() {
        dishServicePort = Mockito.mock(IDishServicePort.class);
        dishRequestMapper = Mockito.mock(IDishRequestMapper.class);
        dishResponseMapper = Mockito.mock(IDishResponseMapper.class);
        dishControllerAdapter = new DishControllerAdapter(dishServicePort, dishRequestMapper,dishResponseMapper);
    }

    @Test
     void testAddDish() {
        AddDishRequest request = new AddDishRequest("Test Dish", "Test Description", 10.0f, "Test URL", "Test Category", 1L);
        Dish dish = new Dish("Test Dish", 10.0, "Test Description", "Test URL", "Test Category", null, 1L);

        when(dishRequestMapper.addRequestToDish(request)).thenReturn(dish);

        ResponseEntity<Void> response = dishControllerAdapter.addDish(request);

        verify(dishServicePort).saveDish(dish);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

     @Test
     void testUpdateDish() {

         Long idRestaurant = 1L;
         AddDishUpdapteRequest request = new AddDishUpdapteRequest(1L,  165.8, "Updated Description", idRestaurant);

         ResponseEntity<Void> response = dishControllerAdapter.updateDish(request);

         verify(dishServicePort).updateDish(request.getId(), Optional.of(request.getPrice()), Optional.of(request.getDescription()),
                 idRestaurant);
         assertEquals(HttpStatus.OK, response.getStatusCode());
     }

     @Test
     void testChangeStatus() {
         Long id = 1L;

         Long idRestaurant = 1L;

         Mockito.doNothing().when(dishServicePort).changeStatus(id, idRestaurant);

         ResponseEntity<Void> response = dishControllerAdapter.changeStatus(id, idRestaurant);

         verify(dishServicePort).changeStatus(id, idRestaurant);
         assertEquals(HttpStatus.OK, response.getStatusCode());
     }

    @Test
    void testGetAllDishes() {
        // Arrange
        Integer page = 0;
        Integer size = 10;
        String category = "Test Category";
        List<Dish> dishes = List.of(new Dish("Test Dish", 10.0, "Test Description", "Test URL", "Test Category", null, 1L));
        List<DishResponse> dishResponses = List.of(new DishResponse(1L,"Test Dish", 10.0, "Test Description",
                "Test URL", "Test Category" ));

        when(dishServicePort.getAllDishes(page, size, category)).thenReturn(dishes);
        when(dishResponseMapper.toDishResponseList(dishes)).thenReturn(dishResponses);

        ResponseEntity<List<DishResponse>> response = dishControllerAdapter.getAllDishes(page, size, category);


        verify(dishServicePort, times(1)).getAllDishes(page, size, category);
        assertEquals(ResponseEntity.ok(dishResponses), response);
    }

}