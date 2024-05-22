package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;

import com.example.microservice_small_square.adapters.driven.driving.http.util.SecurityService;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PermissionDeniedException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.ValueAlreadyExitsException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.example.microservice_small_square.adapters.driven.utils.services.RoleValidationService;
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

    private final SecurityService securityService;

    private static final String RESTAURANT_EXISTS_ERROR_MESSAGE = "The Restaurant";

    private static final String OWNER_ERROR_MESSAGE = "the user";

    private static final String DISH_ERROR_MESSAGE = "The dish";

    @Override
    public void saveDish(Dish dish) {
        String normalizedDishName = dish.getName().toLowerCase();
        dish.setName(normalizedDishName);
        Long idRestaurant = dish.getRestaurant().getId();
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(idRestaurant);
        if (restaurantEntity.isEmpty()) {
            throw new DataNotFoundException(RESTAURANT_EXISTS_ERROR_MESSAGE);
        }
        RestaurantEntity restaurant = restaurantEntity.get();
        Long userId = securityService.getUserIdFromContext();
        if(restaurant.getOwnerId()!= userId){
            throw new PermissionDeniedException(OWNER_ERROR_MESSAGE);
        }
        Optional<DishEntity> dishEntity = dishRepository.findByRestaurantIdAndName(idRestaurant,dish.getName());

        if (dishEntity.isPresent()) {
            throw new ValueAlreadyExitsException(DISH_ERROR_MESSAGE);
        }
        dish.setRestaurant(restaurantEntityMapper.toModel(restaurant));
        dishRepository.save(dishEntityMapper.toEntity(dish));
    }

    @Override
    public void updateDish(Long id, Optional<Double> price, Optional<String> description) {
        Optional<DishEntity> dishEntityOptional = dishRepository.findById(id);

        if (!dishEntityOptional.isPresent()) {
            throw new DataNotFoundException(DISH_ERROR_MESSAGE);
        }

        DishEntity dishEntity = dishEntityOptional.get();

        Long idRestaurant = dishEntity.getRestaurant().getId();

        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(idRestaurant);

        if (!restaurantEntity.isPresent()) {
            throw new DataNotFoundException(DISH_ERROR_MESSAGE);
        }

        RestaurantEntity restaurant = restaurantEntity.get();
        Long userId = securityService.getUserIdFromContext();
        if(restaurant.getOwnerId()!= userId){
            throw new PermissionDeniedException(OWNER_ERROR_MESSAGE);
        }

        price.ifPresent(dishEntity::setPrice);
        description.ifPresent(dishEntity::setDescription);

        dishRepository.save(dishEntity);
    }

    @Override
    public void changeStatus(Long id) {
        Optional<DishEntity> dishEntityOptional = dishRepository.findById(id);

        if (!dishEntityOptional.isPresent()) {
            throw new DataNotFoundException(DISH_ERROR_MESSAGE);
        }

        DishEntity dishEntity = dishEntityOptional.get();

        Long idRestaurant = dishEntity.getRestaurant().getId();

        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(idRestaurant);

        if (!restaurantEntity.isPresent()) {
            throw new DataNotFoundException(DISH_ERROR_MESSAGE);
        }



        RestaurantEntity restaurant = restaurantEntity.get();
        Long userId = securityService.getUserIdFromContext();
        if(restaurant.getOwnerId()!= userId){
            throw new PermissionDeniedException(OWNER_ERROR_MESSAGE);
        }

        dishEntity.setIsActived(!dishEntity.getIsActived());

        dishRepository.save(dishEntity);
    }
}
