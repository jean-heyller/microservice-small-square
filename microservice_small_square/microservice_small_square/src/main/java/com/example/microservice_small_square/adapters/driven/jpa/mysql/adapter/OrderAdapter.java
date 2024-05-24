package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;


import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IOrderRepository;

import com.example.microservice_small_square.adapters.driven.utils.enums.OrderStatus;
import com.example.microservice_small_square.domain.model.DishQuantify;
import com.example.microservice_small_square.domain.model.Order;
import com.example.microservice_small_square.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
public class OrderAdapter implements IOrderPersistencePort {

    private final IDishRepository dishRepository;

    private final IDishEntityMapper dishEntityMapper;

    private final IOrderRepository orderRepository;

    private final IOrderEntityMapper orderEntityMapper;

    private static final String STATUS_PENDING = "PENDING";

    private static final String ERROR_MESSAGE = "The dish ";



    @Override
    public void saveOrder(Order order) {

        ArrayList<String> allowed = new ArrayList<>(Arrays.asList(STATUS_PENDING, "READY", "PREPARATION"));

        List<OrderEntity> existingOrders = orderRepository.findByIdClientAndStatusIn(order.getIdClient(), allowed);
        if (!existingOrders.isEmpty()) {
            throw new IllegalStateException("El cliente ya tiene un pedido en proceso");
        }

        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setDate(order.getDate());
        orderEntity.setIdChef(order.getIdChef());
        orderEntity.setIdClient(order.getIdClient());
        orderEntity.setIdRestaurant(order.getIdRestaurant());
        orderEntity.setStatus(STATUS_PENDING);

        List<DishEntity> dishEntities = new ArrayList<>();
        for (DishQuantify dishQuantify : order.getDishesQuantify()) {
            DishEntity dishEntity = dishRepository.findByRestaurantIdAndId(order.getIdRestaurant(), dishQuantify.getIdDish())
                    .orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE));
            for (int i = 0; i < dishQuantify.getQuantity(); i++) {
                dishEntities.add(dishEntity);
            }
        }

        List<DishEntity> dishEntitiesCopy = new ArrayList<>(dishEntities);

        orderEntity.setDishes(dishEntitiesCopy);
        orderRepository.save(orderEntity);
    }


    @Override
    public List<Order> getOrders(Integer page, Integer size, String status, Long idRestaurant, Long idClient) {
        Pageable pagination = PageRequest.of(page, size);
        List<OrderEntity> orderEntities = orderRepository.findByIdRestaurantAndStatusAndIdClient(idRestaurant, status, idClient, pagination).getContent();

        return orderEntityMapper.toModelList(orderEntities);
    }

    @Override
    public void updateOrder(Order order) {
        OrderEntity orderEntity = orderRepository.findByIdAndIdClientAndIdRestaurant(order.getId(), order.getIdClient(), order.getIdRestaurant())
                .orElseThrow(() -> new DataNotFoundException("Order"));
        OrderStatus currentStatus = OrderStatus.valueOf(orderEntity.getStatus());
        OrderStatus nextStatus = currentStatus.next();
        if (nextStatus == null) {
            throw new IllegalStateException("No further status update is possible.");
        }
        orderEntity.setStatus(nextStatus.name());
        orderRepository.save(orderEntity);
    }
}
