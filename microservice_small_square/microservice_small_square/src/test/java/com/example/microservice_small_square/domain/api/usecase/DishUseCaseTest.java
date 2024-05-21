package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.exception.SizeMinException;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.model.Restaurant;
import com.example.microservice_small_square.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DishUseCaseTest {

    private IDishPersistencePort dishPersistencePort;
    private DishUseCase dishUseCase;

    @BeforeEach
    public void setup() {
        dishPersistencePort = Mockito.mock(IDishPersistencePort.class);
        dishUseCase = new DishUseCase(dishPersistencePort);
    }

    Restaurant restaurant = new Restaurant(1L, "Test Restaurant", "Test nit"
            , "Test address", "3042912963", "Test email", 1L);

    @Test
    public void testSaveDishWithValidPrice() {

        Dish dish = new Dish("Test Dish", 10.0, "Test Description", "Test URL", "Test Category", restaurant, 1L);
        dishUseCase.saveDish(dish);
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
    public void testSaveDishWithInvalidPrice() {
        Dish dish = new Dish("Test Dish", 0.0, "Test Description", "Test URL", "Test Category", restaurant, 1L);
        assertThrows(SizeMinException.class, () -> dishUseCase.saveDish(dish));
    }
}