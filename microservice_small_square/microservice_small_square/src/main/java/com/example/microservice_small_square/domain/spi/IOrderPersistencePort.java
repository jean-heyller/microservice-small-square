package com.example.microservice_small_square.domain.spi;

import com.example.microservice_small_square.domain.model.Order;

public interface IOrderPersistencePort {
    public void saveOrder(Order order);
}
