package com.example.microservice_small_square.adapters.driven.driving.http.mapper.request;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddRestaurantRequest;
import com.example.microservice_small_square.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IRestaurantRequestMapper {
    @Mapping(target = "id", ignore = true)
    Restaurant addRequestToRestaurant(AddRestaurantRequest restaurantRequest);
}
