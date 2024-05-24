package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.api.IOrderServicePort;
import com.example.microservice_small_square.domain.model.Order;
import com.example.microservice_small_square.domain.spi.IOrderPersistencePort;

import java.util.List;

public class OrderUseCase implements IOrderServicePort {

    private IOrderPersistencePort iOrderPersistencePort;

    public OrderUseCase(IOrderPersistencePort iOrderPersistencePort) {
        this.iOrderPersistencePort = iOrderPersistencePort;
    }
    @Override
    public void saveOrder(Order order) {
        iOrderPersistencePort.saveOrder(order);
    }

    @Override
    public List<Order> getOrders(Integer page, Integer size, String status, Long idRestaurant, Long idClient) {
        return iOrderPersistencePort.getOrders(page, size,status, idRestaurant, idClient);
    }

    @Override
    public void updateOrder(Order order) {
        iOrderPersistencePort.updateOrder(order);
    }


}