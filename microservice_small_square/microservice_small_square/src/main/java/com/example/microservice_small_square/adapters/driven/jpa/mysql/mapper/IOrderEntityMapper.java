package com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.microservice_small_square.domain.model.DishQuantify;
import com.example.microservice_small_square.domain.model.Order;

import org.mapstruct.Mapper;


import java.util.List;


@Mapper(componentModel = "spring")
public interface IOrderEntityMapper {
    OrderEntity toEntity(Order order);

    Order toModel(OrderEntity orderEntity);

    List<Order> toModelList(List<OrderEntity> orderEntities);

    DishQuantify dishEntityToDishQuantify(DishEntity dishEntity);


}
