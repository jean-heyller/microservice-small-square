package com.example.microservice_small_square.domain.spi;

import com.example.microservice_small_square.domain.model.Order;

import java.util.List;

public interface IOrderPersistencePort {
    public void saveOrder(Order order);

    List<Order> getOrders(Integer page, Integer size,String status, Long idRestaurant, Long idClient);
}
