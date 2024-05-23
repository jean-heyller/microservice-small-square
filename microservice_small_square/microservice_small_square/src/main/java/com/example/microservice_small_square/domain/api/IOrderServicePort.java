package com.example.microservice_small_square.domain.api;

import com.example.microservice_small_square.domain.model.Order;

public interface IOrderServicePort {
    public void saveOrder(Order order);

}
