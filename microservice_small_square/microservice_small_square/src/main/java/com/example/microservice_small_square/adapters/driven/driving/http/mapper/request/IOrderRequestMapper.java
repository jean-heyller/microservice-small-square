package com.example.microservice_small_square.adapters.driven.driving.http.mapper.request;


import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddOrderRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddOrderUpdateRequest;

import com.example.microservice_small_square.domain.model.Order;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface IOrderRequestMapper {



    Order addRequestToOrder(AddOrderRequest addOrderRequest);

    Order addUpdateRequestToOrder(AddOrderUpdateRequest addOrderUpdateRequest);
}