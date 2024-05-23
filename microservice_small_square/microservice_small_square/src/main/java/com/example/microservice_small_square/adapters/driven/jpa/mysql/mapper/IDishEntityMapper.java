package com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.model.DishQuantify;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IDishEntityMapper {
    DishEntity toEntity(Dish dish);

    Dish toModel(DishEntity dishEntity);

    List<DishQuantify> toModelist(List<DishEntity> dishEntities);
}
