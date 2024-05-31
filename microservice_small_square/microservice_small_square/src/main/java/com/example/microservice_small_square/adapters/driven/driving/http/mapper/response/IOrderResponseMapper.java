package com.example.microservice_small_square.adapters.driven.driving.http.mapper.response;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.OrderResponse;
import com.example.microservice_small_square.domain.model.Order;


import java.util.List;


public interface IOrderResponseMapper {



    OrderResponse toOrderResponse(Order order);

    List<OrderResponse> toOrderResponseList(List<Order> orders);
}
