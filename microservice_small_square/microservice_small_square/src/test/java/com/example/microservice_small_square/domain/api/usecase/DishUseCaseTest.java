package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.exception.SizeMinException;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.model.Restaurant;
import com.example.microservice_small_square.domain.spi.IDishPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

 class DishUseCaseTest {

    private IDishPersistencePort dishPersistencePort;
    private DishUseCase dishUseCase;

    private final Long idRestaurant = 1L;

    @BeforeEach
    public void setup() {
        dishPersistencePort = Mockito.mock(IDishPersistencePort.class);
        dishUseCase = new DishUseCase(dishPersistencePort);
    }

    Restaurant restaurant = new Restaurant(1L, "Test Restaurant", "Test nit"
            , "Test address", "3042912963", "Test email", 1L);

    @Test
     void testSaveDishWithValidPrice() {

        Dish dish = new Dish("Test Dish", 10.0, "Test Description", "Test URL", "Test Category", restaurant, 1L);
        dishUseCase.saveDish(dish);
        verify(dishPersistencePort).saveDish(dish);
    }

    @Test
     void testSaveDishWithInvalidPrice() {
        Dish dish = new Dish("Test Dish", 0.0, "Test Description", "Test URL", "Test Category", restaurant, 1L);
        assertThrows(SizeMinException.class, () -> dishUseCase.saveDish(dish));
    }

    @Test
    void testUpdateDish() {
        Long id = 1L;
        Optional<Double> price = Optional.of(15.0);
        Optional<String> description = Optional.of("Updated Dish");


        dishUseCase.updateDish(id, price, description,idRestaurant);

        verify(dishPersistencePort).updateDish(id, price, description,idRestaurant);
    }

     @Test
     void testChangeStatus() {
         Long id = 1L;

         dishUseCase.changeStatus(id,idRestaurant);

         verify(dishPersistencePort).changeStatus(id,idRestaurant);
     }

     @Test
     void testGetAllDishes() {
         Integer page = 0;
         Integer size = 10;
         String category = "Test Category";

         dishUseCase.getAllDishes(page, size, category);

         verify(dishPersistencePort, times(1)).getAllDishes(page, size, category);
     }


}