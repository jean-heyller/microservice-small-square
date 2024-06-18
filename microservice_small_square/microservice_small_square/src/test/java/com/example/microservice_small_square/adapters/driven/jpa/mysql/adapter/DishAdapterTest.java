package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;

import com.example.microservice_small_square.adapters.driven.driving.http.util.SecurityService;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PermissionDeniedException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.example.microservice_small_square.adapters.driven.utils.services.ClientService;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class DishAdapterTest {

    private IDishRepository dishRepository;
    private IDishEntityMapper dishEntityMapper;
    private IRestaurantRepository restaurantRepository;
    private IRestaurantEntityMapper restaurantEntityMapper;
    private ClientService clientService;
    private DishAdapter dishAdapter;
    private SecurityService securityService;

    @BeforeEach
    public void setup() {
        dishRepository = Mockito.mock(IDishRepository.class);
        dishEntityMapper = Mockito.mock(IDishEntityMapper.class);
        restaurantRepository = Mockito.mock(IRestaurantRepository.class);
        restaurantEntityMapper = Mockito.mock(IRestaurantEntityMapper.class);
        clientService = Mockito.mock(ClientService.class);
        securityService = Mockito.mock(SecurityService.class);
        dishAdapter = new DishAdapter(dishRepository, dishEntityMapper, restaurantRepository,
                restaurantEntityMapper, clientService, securityService);
    }

    @Test
     void testSaveDishWithValidRole() {
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", "Test nit"
                , "Test address", "3042912963", "Test email", 1L);

        Dish dish = new Dish("Test Dish", 10.0, "Test Description", "Test URL", "Test Category", restaurant, 1L);
        RestaurantEntity restaurantEntity = new RestaurantEntity();
        restaurantEntity.setOwnerId(1L);

        when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));
        when(securityService.getUserIdFromContext()).thenReturn(1L);

        dishAdapter.saveDish(dish);
        verify(dishRepository).save(dishEntityMapper.toEntity(dish));
    }

     @Test
     void testSaveDishWithInvalidRole() {
         Restaurant restaurant = new Restaurant(1L, "Test Restaurant", "Test nit"
                 , "Test address", "3042912963", "Test email", 1L);

         Dish dish = new Dish("Test Dish", 10.0, "Test Description", "Test URL", "Test Category", restaurant, 1L);
         RestaurantEntity restaurantEntity = new RestaurantEntity();
         restaurantEntity.setOwnerId(2L);

         when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurantEntity));
         when(securityService.getUserIdFromContext()).thenReturn(1L);
         assertThrows(PermissionDeniedException.class, () -> dishAdapter.saveDish(dish));
     }

     @Test
     void testSaveDishWithNonExistentRestaurant() {
         Restaurant restaurant = new Restaurant(2L, "New Test Restaurant", "New Test nit"
                 , "New Test address", "3042912964", "New Test email", 2L);

         Dish dish = new Dish("New Test Dish", 20.0, "New Test Description", "New Test URL", "New Test Category", restaurant, 2L);

         when(restaurantRepository.findById(2L)).thenReturn(Optional.empty());

         assertThrows(DataNotFoundException.class, () -> dishAdapter.saveDish(dish));
     }
     @Test
     void testUpdateDish() {
         Long id = 2L;
         Optional<Double> price = Optional.of(20.0);
         Optional<String> description = Optional.of("New Updated Dish");

         DishEntity dishEntity = new DishEntity();
         dishEntity.setId(id);
         dishEntity.setPrice(price.get());
         dishEntity.setDescription(description.get());

         RestaurantEntity restaurantEntity = new RestaurantEntity();
         restaurantEntity.setId(1L);
         restaurantEntity.setOwnerId(1L);

         dishEntity.setRestaurant(restaurantEntity); // Asociamos el RestaurantEntity al DishEntity

         when(dishRepository.findByRestaurantIdAndId(1L, id)).thenReturn(Optional.of(dishEntity));
         when(restaurantRepository.findById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));
         when(securityService.getUserIdFromContext()).thenReturn(1L);

         dishAdapter.updateDish(id, price, description, 1L);

         verify(dishRepository).findByRestaurantIdAndId(1L, id);
         verify(dishRepository).save(dishEntity);
     }


     @Test
     void testUpdateDishWithInvalidUserId() {
         Long id = 2L;
         Long restaurantId = 1L;
         Optional<Double> price = Optional.of(20.0);
         Optional<String> description = Optional.of("New Updated Dish");

         DishEntity dishEntity = new DishEntity();
         dishEntity.setId(id);
         dishEntity.setPrice(price.get());
         dishEntity.setDescription(description.get());

         RestaurantEntity restaurantEntity = new RestaurantEntity();
         restaurantEntity.setId(restaurantId);
         restaurantEntity.setOwnerId(1L); // El ownerId es 1
         dishEntity.setRestaurant(restaurantEntity);

         when(dishRepository.findByRestaurantIdAndId(restaurantId, id)).thenReturn(Optional.of(dishEntity));
         when(restaurantRepository.findById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));
         when(securityService.getUserIdFromContext()).thenReturn(2L); // El userId es 2, no coincide con el ownerId

         assertThrows(PermissionDeniedException.class, () -> dishAdapter.updateDish(id, price, description, restaurantId));
     }

     @Test
     void testChangeStatus() {
         Long id = 1L;
         Long userId = 1L;

         RestaurantEntity restaurantEntity = new RestaurantEntity();
         restaurantEntity.setOwnerId(userId);

         DishEntity dishEntity = new DishEntity();
         dishEntity.setIsActived(false);
         dishEntity.setRestaurant(restaurantEntity);

         when(dishRepository.findByRestaurantIdAndId(1L, id)).thenReturn(Optional.of(dishEntity));
         when(restaurantRepository.findById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));
         when(securityService.getUserIdFromContext()).thenReturn(userId);

         dishAdapter.changeStatus(id, 1L);

         assertTrue(dishEntity.getIsActived());
         verify(dishRepository).save(dishEntity);
     }

     @Test
     void testChangeStatusPermissionDenied() {
         Long id = 1L;
         Long userId = 1L;
         Long ownerId = 2L;
         Long restaurantId = 1L;

         RestaurantEntity restaurantEntity = new RestaurantEntity();
         restaurantEntity.setId(restaurantId);
         restaurantEntity.setOwnerId(ownerId);

         DishEntity dishEntity = new DishEntity();
         dishEntity.setIsActived(false);
         dishEntity.setRestaurant(restaurantEntity);

         when(dishRepository.findByRestaurantIdAndId(restaurantId, id)).thenReturn(Optional.of(dishEntity));
         when(restaurantRepository.findById(restaurantEntity.getId())).thenReturn(Optional.of(restaurantEntity));
         when(securityService.getUserIdFromContext()).thenReturn(userId);

         assertThrows(PermissionDeniedException.class, () -> dishAdapter.changeStatus(id, restaurantId));
     }

    @Test
    void testGetAllDishes() {

        Integer page = 0;
        Integer size = 10;
        String category = "Test Category";
        List<DishEntity> dishEntities = Arrays.asList(
                new DishEntity(1L, "Test Dish 1", "Test Description 1", 10.0, "Test URL 1", "Test Category 1", true, new RestaurantEntity()),
                new DishEntity(2L, "Test Dish 2", "Test Description 2", 20.0, "Test URL 2", "Test Category 2", true, new RestaurantEntity())
        );
        Page<DishEntity> pagedDishes = new PageImpl<>(dishEntities);
        List<Dish> expectedDishes = dishEntities.stream()
                .map(dishEntityMapper::toModel)
                .toList();

        when(dishRepository.findAll(PageRequest.of(page, size))).thenReturn(pagedDishes);
        when(dishRepository.findAllByCategory(category, PageRequest.of(page, size))).thenReturn(pagedDishes);


        List<Dish> actualDishesWithoutCategory = dishAdapter.getAllDishes(page, size, null);
        List<Dish> actualDishesWithCategory = dishAdapter.getAllDishes(page, size, category);

        verify(dishRepository, times(1)).findAll(PageRequest.of(page, size));
        verify(dishRepository, times(1)).findAllByCategory(category, PageRequest.of(page, size));
        assertEquals(expectedDishes, actualDishesWithoutCategory);
        assertEquals(expectedDishes, actualDishesWithCategory);
    }
}