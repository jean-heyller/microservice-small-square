package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;


import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PermissionDeniedException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.example.microservice_small_square.adapters.driven.utils.services.ClientService;
import com.example.microservice_small_square.domain.model.Restaurant;
import com.example.microservice_small_square.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.util.List;


@RequiredArgsConstructor
public class RestaurantAdapter implements IRestaurantPersistencePort {

    private static final String ERROR_MESSAGE = "User";

    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final ClientService clientService;


    @Override
    public void saveRestaurant(Restaurant restaurant) {
        boolean allowed = clientService.validateUserRole(restaurant.getOwnerId(), "OWNER");
        if (!allowed) {
            throw new PermissionDeniedException(ERROR_MESSAGE);
        }
        restaurantRepository.save(restaurantEntityMapper.toEntity(restaurant));
    }

    @Override
    public List<Restaurant> getAllRestaurants(Integer page, Integer size) {
        return restaurantRepository.findAll(PageRequest.of(page, size))
                .stream()
                .map(restaurantEntityMapper::toModel)
                .toList();
    }
}
