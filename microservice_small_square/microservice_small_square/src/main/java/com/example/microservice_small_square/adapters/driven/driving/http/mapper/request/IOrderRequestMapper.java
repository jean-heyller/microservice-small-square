package com.example.microservice_small_square.adapters.driven.driving.http.mapper.request;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.personalized.AddDishType;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddOrderRequest;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderRequestMapper {

    @Mapping(target = "name", constant = "dishName")
    @Mapping(target = "price", constant = "10.20")
    @Mapping(target = "imageUrl", constant = "imageUrl")
    @Mapping(target = "category", constant = "category")
    Dish addDishTypeToDish(AddDishType addDishType);


    Order addRequestToOrder(AddOrderRequest addOrderRequest);
}