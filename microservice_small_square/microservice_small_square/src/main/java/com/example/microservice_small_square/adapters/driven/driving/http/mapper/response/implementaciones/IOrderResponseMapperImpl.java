package com.example.microservice_small_square.adapters.driven.driving.http.mapper.response.implementaciones;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.DishResponse;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.OrderResponse;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.response.IDishResponseMapper;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.response.IOrderResponseMapper;
import com.example.microservice_small_square.domain.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class IOrderResponseMapperImpl implements IOrderResponseMapper {
    private final IDishResponseMapper dishResponseMapper;

    public IOrderResponseMapperImpl(IDishResponseMapper dishResponseMapper) {
        this.dishResponseMapper = dishResponseMapper;
    }

    @Override
    public OrderResponse toOrderResponse(Order order) {
        List<DishResponse> dishResponses = dishResponseMapper.toDishResponseList(order.getDishes());
        return new OrderResponse(order.getId(), order.getDate(), order.getIdChef(), dishResponses, order.getIdClient(), order.getIdRestaurant());
    }

    @Override
    public List<OrderResponse> toOrderResponseList(List<Order> orders) {
        return orders.stream()
                .map(this::toOrderResponse)
                .collect(Collectors.toList());
    }
}
