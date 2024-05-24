package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.NoMessagesFoundException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.OrderStateUpdateException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PendingOrderExistsException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IOrderRepository;
import com.example.microservice_small_square.adapters.driven.sms.SmMService;
import com.example.microservice_small_square.adapters.driven.utils.services.RoleValidationService;
import com.example.microservice_small_square.domain.model.Dish;
import com.example.microservice_small_square.domain.model.DishQuantify;
import com.example.microservice_small_square.domain.model.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OrderAdapterTest {

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @Mock
    private IOrderRepository orderRepository;

    @Mock
    private SmMService smmService;

    @Mock
    private RoleValidationService roleValidationService;

    @Mock
    private IOrderEntityMapper orderEntityMapper;

    private OrderAdapter orderAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderAdapter = new OrderAdapter(dishRepository, dishEntityMapper, orderRepository, orderEntityMapper, smmService, roleValidationService);
    }


    @Test
     void testSaveOrder_ThrowsDataNotFoundException() {
        DishQuantify dishQuantify = new DishQuantify(1L,1);
        Order order = new Order(1L,1L, LocalDate.now(),1L, Collections.singletonList(dishQuantify),1L);

        when(dishRepository.findByRestaurantIdAndId(order.getIdRestaurant(), dishQuantify.getIdDish())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> orderAdapter.saveOrder(order));
    }


    @Test
     void testSaveOrder_Success() {
        DishQuantify dishQuantify = new DishQuantify(1L,1);
        Order order = new Order(1L,1L, LocalDate.now(),1L, Collections.singletonList(dishQuantify),1L);

        DishEntity dishEntity = new DishEntity();
        when(dishRepository.findByRestaurantIdAndId(order.getIdRestaurant(), dishQuantify.getIdDish())).thenReturn(Optional.of(dishEntity));

        OrderEntity orderEntity = new OrderEntity();
        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);

        orderAdapter.saveOrder(order);

        verify(orderRepository).save(any(OrderEntity.class));
    }

    @Test
     void testGetOrders_Success() {
        Page<OrderEntity> page = new PageImpl<>(Collections.emptyList());

        when(orderRepository.findByIdRestaurantAndStatusAndIdClient(anyLong(), anyString(), anyLong(), any())).thenReturn(page);

        List<Order> orders = orderAdapter.getOrders(0, 10, "PENDING", 1L, 1L);

        verify(orderRepository).findByIdRestaurantAndStatusAndIdClient(anyLong(), anyString(), anyLong(), any());
        assertTrue(orders.isEmpty());
    }

    @Test
     void testUpdateOrder_DataNotFoundException() {
        DishQuantify dishQuantify = new DishQuantify(1L,1);
        Order order = new Order(1L,1L, LocalDate.now(),1L, Collections.singletonList(dishQuantify),1L);
        when(orderRepository.findByIdAndIdClientAndIdRestaurant(anyLong(), anyLong(), anyLong())).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class, () -> orderAdapter.updateOrder(order));
    }

    @Test
     void testUpdateOrder_Success() {
        DishQuantify dishQuantify = new DishQuantify(1L,1);
        Order order = new Order(1L,1L, LocalDate.now(),1L, Collections.singletonList(dishQuantify),1L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus("PREPARATION");
        when(orderRepository.findByIdAndIdClientAndIdRestaurant(anyLong(), anyLong(), anyLong())).thenReturn(Optional.of(orderEntity));
        when(roleValidationService.getPhoneNumber(anyLong())).thenReturn("1234567890");
        when(smmService.retrieveMessage(anyString())).thenReturn("Test message");

        orderAdapter.updateOrder(order);

        verify(orderRepository).save(any(OrderEntity.class));
    }


    @Test
     void testUpdateOrder_NoMessagesFoundException() {
        DishQuantify dishQuantify = new DishQuantify(1L,1);
        Order order = new Order(1L,1L, LocalDate.now(),1L, Collections.singletonList(dishQuantify),1L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus("PREPARATION");
        when(orderRepository.findByIdAndIdClientAndIdRestaurant(anyLong(), anyLong(), anyLong())).thenReturn(Optional.of(orderEntity));
        when(roleValidationService.getPhoneNumber(anyLong())).thenReturn("1234567890");
        when(smmService.retrieveMessage(anyString())).thenReturn(null);

        assertThrows(NoMessagesFoundException.class, () -> orderAdapter.updateOrder(order));
    }

    @Test
     void testUpdateOrder_OrderStateUpdateException() {
        DishQuantify dishQuantify = new DishQuantify(1L,1);
        Order order = new Order(1L,1L, LocalDate.now(),1L, Collections.singletonList(dishQuantify),1L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus("DELIVERED");
        when(orderRepository.findByIdAndIdClientAndIdRestaurant(anyLong(), anyLong(), anyLong())).thenReturn(Optional.of(orderEntity));

        assertThrows(OrderStateUpdateException.class, () -> orderAdapter.updateOrder(order));
    }

}