package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.personalized.AddDishType;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddOrderRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddOrderUpdateRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.OrderResponse;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.IOrderRequestMapper;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.response.IOrderResponseMapper;
import com.example.microservice_small_square.domain.api.IOrderServicePort;
import com.example.microservice_small_square.domain.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderControllerAdapterTest {

    @Mock
    private IOrderServicePort orderServicePort;

    @Mock
    private IOrderRequestMapper orderRequestMapper;

    private OrderControllerAdapter orderControllerAdapter;

    private IOrderResponseMapper orderResponseMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderResponseMapper = mock(IOrderResponseMapper.class);
        orderControllerAdapter = new OrderControllerAdapter(orderServicePort, orderRequestMapper, orderResponseMapper);
    }
    @Test
     void testAddOrder() {
        AddOrderRequest addOrderRequest = new AddOrderRequest(1L, LocalDate.now(), 1L,
                Collections.singletonList(new AddDishType(1L, 1)), 1L, 1L);
        Order order = new Order(1L, 1L, LocalDate.now(), 1L, Collections.emptyList(), 1L);

        when(orderRequestMapper.addRequestToOrder(addOrderRequest)).thenReturn(order);

        ResponseEntity<Void> response = orderControllerAdapter.addOrder(addOrderRequest);

        verify(orderServicePort).saveOrder(order);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
     void testGetOrder_Success() {
        List<Order> orders = Collections.singletonList(new Order(1L,1L, LocalDate.now(),1L, Collections.emptyList(),1L));
        when(orderServicePort.getOrders(0, 10, "PENDING", 1L, 1L)).thenReturn(orders);

        List<OrderResponse> orderResponses = Collections.singletonList(new OrderResponse(1L, LocalDate.now(), 1L, Collections.emptyList(), 1L, 1L));
        when(orderResponseMapper.toOrderResponseList(orders)).thenReturn(orderResponses);

        ResponseEntity<List<OrderResponse>> response = orderControllerAdapter.getOrder(0, 10, "PENDING", 1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(orderResponses, response.getBody());
    }

    @Test
     void testGetOrder_AccessDenied() {
        when(orderServicePort.getOrders(0, 10, "PENDING", 1L, 1L)).thenThrow(new AccessDeniedException("Access is denied"));

        assertThrows(AccessDeniedException.class, () -> orderControllerAdapter.getOrder(0, 10, "PENDING", 1L, 1L));
    }

    @Test
    public void testUpdateOrder() {
        AddOrderUpdateRequest request = new AddOrderUpdateRequest(1L, 1L, 1L, 1L);
        Order order = new Order(1L, 1L, LocalDate.now(), 1L, Collections.emptyList(), 1L);
        when(orderRequestMapper.addUpdateRequestToOrder(request)).thenReturn(order);

        ResponseEntity<Void> response = orderControllerAdapter.updateOrder(request);

        verify(orderServicePort).updateOrder(order);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}