package com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.RestaurantEntity;
import com.example.microservice_small_square.domain.model.Restaurant;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRestaurantEntityMapper {
    RestaurantEntity toEntity(Restaurant restaurant);

    Restaurant toModel(RestaurantEntity restaurantEntity);
}
