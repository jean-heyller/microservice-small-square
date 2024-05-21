package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.api.IRestaurantServicePort;
import com.example.microservice_small_square.domain.model.Restaurant;
import com.example.microservice_small_square.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
}