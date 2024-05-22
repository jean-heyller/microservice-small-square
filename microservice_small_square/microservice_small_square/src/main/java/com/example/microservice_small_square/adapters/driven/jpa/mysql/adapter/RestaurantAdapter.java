package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PermissionDeniedException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.utils.RoleValidationService;
import com.example.microservice_small_square.domain.model.Restaurant;
import com.example.microservice_small_square.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantAdapter implements IRestaurantPersistencePort {

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final RoleValidationService roleValidationService;

    private static final String MESSAGE_ERROR_USER = "the user ";
    private static final String OWNER = "OWNER";

    @Override
    public void saveRestaurant(Restaurant restaurant) {
        boolean validate =  roleValidationService.validateUserRole(restaurant.getOwnerId(), OWNER);
        if(!validate){
            throw new PermissionDeniedException(MESSAGE_ERROR_USER);
        }
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }
}
