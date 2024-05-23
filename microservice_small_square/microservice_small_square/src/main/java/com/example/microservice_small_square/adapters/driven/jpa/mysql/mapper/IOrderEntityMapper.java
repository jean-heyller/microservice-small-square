package com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.microservice_small_square.domain.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IOrderEntityMapper {
    OrderEntity toEntity(Order order);

    Order toModel(OrderEntity orderEntity);
}
