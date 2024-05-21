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

    private static final String DISH_EXISTS_ERROR_MESSAGE = "The Restaurant";

    private static final String OWNER_ERROR_MESSAGE = "the user";

    @Override
    public void saveDish(Dish dish) {
        String normalizedDishName = dish.getName().toLowerCase();
        dish.setName(normalizedDishName);
        Long idRestaurant = dish.getRestaurant().getId();
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(idRestaurant);
        if (restaurantEntity.isEmpty()) {
            throw new DataNotFoundException(DISH_EXISTS_ERROR_MESSAGE);
        }
        RestaurantEntity restaurant = restaurantEntity.get();
        Long idOwner = restaurant.getOwnerId();
        boolean validate = roleValidationService.validateUserRole(idOwner, "propietario");
        if (!validate) {
            throw new PermissionDeniedException(OWNER_ERROR_MESSAGE);
        }
        dish.setRestaurant(restaurantEntityMapper.toModel(restaurant));
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }

    @Override
    public void updateDish(Long id, Optional<Double> price, Optional<String> description) {
        Optional<DishEntity> dishEntityOptional = dishRepository.findById(id);

        if (!dishEntityOptional.isPresent()) {
            throw new RuntimeException("Dish not found");
        }

        DishEntity dishEntity = dishEntityOptional.get();

        price.ifPresent(dishEntity::setPrice);
        description.ifPresent(dishEntity::setDescription);

        dishRepository.save(dishEntity);
    }


}
