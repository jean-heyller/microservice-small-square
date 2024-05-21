package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PermissionDeniedException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.utils.RoleValidationService;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DishAdapterTest {

    private IDishRepository dishRepository;
    private IDishEntityMapper dishEntityMapper;
    private IRestaurantRepository restaurantRepository;
    private IRestaurantEntityMapper restaurantEntityMapper;
    private RoleValidationService roleValidationService;
    private DishAdapter dishAdapter;

    @BeforeEach
    public void setup() {
        dishRepository = Mockito.mock(IDishRepository.class);
        dishEntityMapper = Mockito.mock(IDishEntityMapper.class);
        restaurantRepository = Mockito.mock(IRestaurantRepository.class);
        restaurantEntityMapper = Mockito.mock(IRestaurantEntityMapper.class);
        roleValidationService = Mockito.mock(RoleValidationService.class);
        dishAdapter = new DishAdapter(dishRepository, dishEntityMapper, restaurantRepository, restaurantEntityMapper, roleValidationService);
    }

    @Test
    public void testSaveDishWithValidRole() {
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", "Test nit"
                , "Test address", "3042912963", "Test email", 1L);

        Dish dish = new Dish("Test Dish", 10.0, "Test Description", "Test URL", "Test Category", restaurant, 1L);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setOwnerId(1L);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));
        when(roleValidationService.validateUserRole(1L, "propietario")).thenReturn(true);

        dishAdapter.saveDish(dish);

        verify(dishRepository).save(dishEntityMapper.toEntity(dish));
    }

    @Test
    public void testSaveDishWithInvalidRole() {
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", "Test nit"
                , "Test address", "3042912963", "Test email", 1L);

        Dish dish = new Dish("Test Dish", 10.0, "Test Description", "Test URL", "Test Category", restaurant, 1L);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setOwnerId(1L);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));
        when(roleValidationService.validateUserRole(1L, "propietario")).thenReturn(false);

        assertThrows(PermissionDeniedException.class, () -> dishAdapter.saveDish(dish));
    }

    @Test
    public void testSaveDishWithNonExistentRestaurant() {
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", "Test nit"
                , "Test address", "3042912963", "Test email", 1L);

        Dish dish = new Dish("Test Dish", 10.0, "Test Description", "Test URL", "Test Category", restaurant, 1L);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> dishAdapter.saveDish(dish));
    }

    @Test
    void testUpdateDish() {
        Long id = 1L;
        Optional<Double> price = Optional.of(15.0);
        Optional<String> description = Optional.of("Updated Dish");

        DishEntity dishEntity = new DishEntity();
        dishEntity.setId(id);
        dishEntity.setPrice(price.get());
        dishEntity.setDescription(description.get());

        when(dishRepository.findById(id)).thenReturn(Optional.of(dishEntity));

        dishAdapter.updateDish(id, price, description);

        verify(dishRepository).findById(id);
        verify(dishRepository).save(dishEntity);
    }
}