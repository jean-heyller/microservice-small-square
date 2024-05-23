package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PendingOrderExistsException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IOrderRepository;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.model.DishQuantify;
import com.example.microservice_small_square.domain.model.Order;
import com.example.microservice_small_square.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
public class OrderAdapter implements IOrderPersistencePort {

    private final IDishRepository dishRepository;

    private final IDishEntityMapper dishEntityMapper;

    private final IOrderRepository orderRepository;

    private final IOrderEntityMapper orderEntityMapper;

    private static final String STATUS_PENDING = "PENDING";

    private  static final  String ERROR_MESSAGE = "The dish ";
    @Override
    public void saveOrder(Order order) {
        List<DishQuantify> dishes = order.getDishes();

        if (orderRepository.findByIdRestaurantAndStatus(order.getIdRestaurant(), STATUS_PENDING).isPresent()) {
            throw new PendingOrderExistsException();
        }



        List<DishEntity> dishEntities = new ArrayList<>();
        for (DishQuantify dishQuantify : dishes) {
            DishEntity dishEntity = dishRepository.findByRestaurantIdAndId(order.getIdRestaurant(),dishQuantify.getIdDish())
                    .orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE));
            for (int i = 0; i < dishQuantify.getQuantity(); i++) {
                dishEntities.add(dishEntity);
            }
        }

        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        orderEntity.setDishes(dishEntities);
        orderRepository.save(orderEntity);
    }
}
