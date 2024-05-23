package com.example.microservice_small_square.adapters.driven.driving.http.mapper.response;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.DishResponse;
import com.example.microservice_small_square.domain.model.Dish;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface IDishResponseMapper {
    List<DishResponse> toDishResponseList(List<Dish> dishes);
}
