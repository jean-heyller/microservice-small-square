package com.example.microservice_small_square.adapters.driven.driving.http.mapper;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishRequest;
import com.example.microservice_small_square.domain.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IDishRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "restaurant.name", constant = "restaurantName")
    @Mapping(target = "restaurant.id", source = "restaurantId" )
    @Mapping(target = "restaurant.description", constant = "restaurantDescription")
    Dish addRequestToDish(AddDishRequest addDishRequest);
}
