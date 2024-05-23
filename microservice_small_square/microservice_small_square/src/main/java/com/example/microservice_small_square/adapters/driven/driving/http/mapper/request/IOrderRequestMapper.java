package com.example.microservice_small_square.adapters.driven.driving.http.mapper.request;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddOrderRequest;
import com.example.microservice_small_square.domain.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderRequestMapper {


    @Mapping(target = "dish.name", constant = "dishName")
    @Mapping(target = "dish.price", constant = "10.20")
    @Mapping(target = "dish.imageUrl", constant = "imageUrl")
    @Mapping(target = "dish.category", constant = "category")
    Order addRequestToOrder(AddOrderRequest addOrderRequest);
}
