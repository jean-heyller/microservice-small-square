package com.example.microservice_small_square.adapters.driven.driving.http.mapper.response;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.RestaurantResponse;
import com.example.microservice_small_square.domain.model.Restaurant;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IRestaurantResponseMapper {
    List<RestaurantResponse> toRestaurantResponseList(List<Restaurant> restaurants);
}
