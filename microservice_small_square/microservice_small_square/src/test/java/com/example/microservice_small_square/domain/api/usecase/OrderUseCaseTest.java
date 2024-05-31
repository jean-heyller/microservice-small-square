package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.model.DishQuantify;
import com.example.microservice_small_square.domain.model.Order;
import com.example.microservice_small_square.domain.spi.IOrderPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;


import static org.mockito.Mockito.verify;

class OrderUseCaseTest {


    @Mock
    private IOrderPersistencePort orderPersistencePort;

    private OrderUseCase orderUseCase;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        orderUseCase = new OrderUseCase(orderPersistencePort);
    }

    @Test
     void testSaveOrder() {
        Order order = new Order(1L, 1L, LocalDate.now(), 1L, Collections.emptyList(), 1L);
        DishQuantify dishQuantify = new DishQuantify(1L, 1);


        orderUseCase.saveOrder(order);

        verify(orderPersistencePort).saveOrder(order);
    }

    @Test
     void testUpdateOrder() {
        Order order = new Order(1L, 1L, LocalDate.now(), 1L, Collections.emptyList(), 1L);

        orderUseCase.updateOrder(order);

        verify(orderPersistencePort).updateOrder(order);
    }

    @Test
    void testDeleteOrder() {
        Order order = new Order(1L, 1L, LocalDate.now(), 1L, Collections.emptyList(), 1L);

        orderUseCase.deleteOrder(order);

        verify(orderPersistencePort).deleteOrder(order);
    }



}