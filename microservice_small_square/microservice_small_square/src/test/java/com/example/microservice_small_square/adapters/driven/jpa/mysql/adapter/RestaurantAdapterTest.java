package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PermissionDeniedException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.example.microservice_small_square.adapters.driven.utils.services.ClientService;
import com.example.microservice_small_square.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class RestaurantAdapterTest {
    private IRestaurantRepository repository;
    private IRestaurantEntityMapper mapper;
    private ClientService clientService;
    private RestaurantAdapter adapter;



    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(IRestaurantRepository.class);
        mapper = Mockito.mock(IRestaurantEntityMapper.class);
        clientService = Mockito.mock(ClientService.class);
        adapter = new RestaurantAdapter(repository, mapper, clientService);
    }



    @Test
     void testSaveRestaurant_WithInvalidRole() {
        Restaurant restaurant = new Restaurant(1L, "Test Name", "Test Nit", "Test Address", "Test Phone", "Test URL", 1L);
        when(clientService.validateUserRole(restaurant.getOwnerId(), "OWNER")).thenReturn(false);

        assertThrows(PermissionDeniedException.class, () -> adapter.saveRestaurant(restaurant));
    }

    @Test
    void testGetAllRestaurants() {
        // Arrange
        int page = 0;
        int size = 10;
        List<RestaurantEntity> restaurantEntities = List.of(
                new RestaurantEntity(1L, "Test Name 1", "Test Nit 1", "Test Address 1", "Test Phone 1", "Test URL 1", 1L),
                new RestaurantEntity(2L, "Test Name 2", "Test Nit 2", "Test Address 2", "Test Phone 2", "Test URL 2", 2L)
        );
        Page<RestaurantEntity> pagedRestaurants = new PageImpl<>(restaurantEntities);
        when(repository.findAll(PageRequest.of(page, size))).thenReturn(pagedRestaurants);

        List<Restaurant> expectedRestaurants = restaurantEntities.stream()
                .map(mapper::toModel)
                .collect(Collectors.toList());


        List<Restaurant> actualRestaurants = adapter.getAllRestaurants(page, size);


        assertEquals(expectedRestaurants, actualRestaurants);
    }

}