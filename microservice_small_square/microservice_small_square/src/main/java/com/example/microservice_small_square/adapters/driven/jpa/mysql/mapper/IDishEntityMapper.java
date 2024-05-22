package com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.domain.model.Dish;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IDishEntityMapper {
    DishEntity toEntity(Dish dish);

    Dish toModel(DishEntity dishEntity);
}
