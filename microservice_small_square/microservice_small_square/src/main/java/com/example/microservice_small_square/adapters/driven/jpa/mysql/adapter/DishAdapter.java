package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.utils.RoleValidationService;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.spi.IDishPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
@RequiredArgsConstructor
public class DishAdapter implements IDishPersistencePort {

    private final IDishRepository dishRepository;

    private final IDishEntityMapper dishEntityMapper;

    private final IRestaurantRepository restaurantRepository;

    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final RoleValidationService roleValidationService;

    @Override
    public void saveDish(Dish dish) {
        String normalizedDishName = dish.getName().toLowerCase();
        dish.setName(normalizedDishName);
        Long idRestaurant = dish.getRestaurant().getId();
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(idRestaurant);
        if (restaurantEntity.isEmpty()) {
            throw new RuntimeException("The restaurant does not exist");
        }
        RestaurantEntity restaurant = restaurantEntity.get();
        Long idOwner = restaurant.getOwnerId();
        boolean validate = roleValidationService.validateUserRole(idOwner, "propietario");
        if (!validate) {
            throw new RuntimeException("the user does not have the necessary permissions");
        }
        dish.setRestaurant(restaurantEntityMapper.toModel(restaurant));
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }



}
