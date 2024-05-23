package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.api.IRestaurantServicePort;
import com.example.microservice_small_square.domain.model.Restaurant;
import com.example.microservice_small_square.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class RestaurantUseCaseTest {

    private IRestaurantPersistencePort persistencePort;
    private IRestaurantServicePort restaurantServicePort;

    @BeforeEach
    void setUp() {
        persistencePort = Mockito.mock(IRestaurantPersistencePort.class);
        restaurantServicePort = new RestaurantUseCase(persistencePort);
    }

    @Test
    void saveRestaurant() {
        Restaurant restaurant = new Restaurant(1L, "Test Name", "Test Nit", "Test Address", "Test Phone", "Test URL", 1L);
        restaurantServicePort.saveRestaurant(restaurant);
        verify(persistencePort, times(1)).saveRestaurant(restaurant);
    }

    @Test
    void getAllRestaurants() {
        // Arrange
        List<Restaurant> testRestaurants = List.of(
                new Restaurant(1L, "Test Name 1", "Test Nit 1", "Test Address 1", "Test Phone 1", "Test URL 1", 1L),
                new Restaurant(2L, "Test Name 2", "Test Nit 2", "Test Address 2", "Test Phone 2", "Test URL 2", 2L)
        );
        when(persistencePort.getAllRestaurants(0, 10)).thenReturn(testRestaurants);


        List<Restaurant> result = restaurantServicePort.getAllRestaurants(0, 10);


        assertEquals(testRestaurants, result);
        verify(persistencePort, times(1)).getAllRestaurants(0, 10);
    }
}