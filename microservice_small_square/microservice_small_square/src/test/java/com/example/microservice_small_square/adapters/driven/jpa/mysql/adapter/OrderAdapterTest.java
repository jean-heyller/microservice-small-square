package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddSmsSenderRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.ITraceabilityRequestMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.NoMessagesFoundException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.OrderStateUpdateException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IOrderRepository;
import com.example.microservice_small_square.adapters.driven.utils.services.ClientService;
import com.example.microservice_small_square.domain.api.ITraceabilityServicePort;
import com.example.microservice_small_square.domain.model.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderAdapterTest {

    @Mock
    private IDishRepository dishRepository;

    @Mock
    private IDishEntityMapper dishEntityMapper;

    @Mock
    private IOrderRepository orderRepository;



    @Mock
    private ClientService clientService;

    @Mock
    private IOrderEntityMapper orderEntityMapper;

    @Mock
    private ITraceabilityServicePort traceabilityServicePort;
    @Mock
    private ITraceabilityRequestMapper traceabilityRequestMapper;

    private OrderAdapter orderAdapter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        orderAdapter = new OrderAdapter(dishRepository, dishEntityMapper, orderRepository,
                orderEntityMapper, clientService,
                traceabilityRequestMapper, traceabilityServicePort);
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
        // Arrange
        DishQuantify dishQuantify = new DishQuantify(1L,1);
        Order order = new Order(1L,1L, LocalDate.now(),1L, Collections.singletonList(dishQuantify),1L);

        DishEntity dishEntity = new DishEntity();
        when(dishRepository.findByRestaurantIdAndId(order.getIdRestaurant(), dishQuantify.getIdDish())).thenReturn(Optional.of(dishEntity));

        OrderEntity orderEntity = new OrderEntity();
        when(orderEntityMapper.toEntity(order)).thenReturn(orderEntity);

        when(orderRepository.findByIdClientAndStatusIn(anyLong(), anyList())).thenReturn(Collections.emptyList());

        Traceability traceability = new Traceability(1L, "id_client", "emailClient", LocalDate.now(), "PREPARATION", "READY", 1L, "emailEmployee", 1L, "status");
        when(traceabilityRequestMapper.toModelOrder(order)).thenReturn(traceability);

        when(clientService.getEmail(order.getIdChef())).thenReturn("emailEmployee");
        when(clientService.getEmail(order.getIdClient())).thenReturn("emailClient");

        // Act
        orderAdapter.saveOrder(order);

        // Assert
        verify(orderRepository).save(any(OrderEntity.class));
        verify(traceabilityServicePort).saveTraceability(traceability);
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
        // Arrange
        DishQuantify dishQuantify = new DishQuantify(1L,1);
        Order order = new Order(1L,1L, LocalDate.now(),1L, Collections.singletonList(dishQuantify),1L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus("PREPARATION");

        Traceability traceability = new Traceability(1L, "id_client", "emailClient", LocalDate.now(), "PREPARATION", "READY", 1L, "emailEmployee", 1L, "status");

        when(orderRepository.findByIdAndIdClientAndIdRestaurant(anyLong(), anyLong(), anyLong())).thenReturn(Optional.of(orderEntity));
        when(clientService.getPhoneNumber(anyLong())).thenReturn("1234567890");
        when(traceabilityRequestMapper.toModelOrder(order)).thenReturn(traceability);

        // Act
        orderAdapter.updateOrder(order);

        // Assert
        verify(orderRepository).save(orderEntity);
        verify(traceabilityServicePort).saveTraceability(traceability);
        assertEquals("READY", orderEntity.getStatus());
    }




    @Test
    void testUpdateOrder_RuntimeException() {
        DishQuantify dishQuantify = new DishQuantify(1L,1);
        Order order = new Order(1L,1L, LocalDate.now(),1L, Collections.singletonList(dishQuantify),1L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus("READY");
        orderEntity.setCodeValidated(false);
        when(orderRepository.findByIdAndIdClientAndIdRestaurant(anyLong(), anyLong(), anyLong())).thenReturn(Optional.of(orderEntity));

        assertThrows(RuntimeException.class, () -> orderAdapter.updateOrder(order));
    }

    @Test
    void testDeleteOrder_StatusPending() {
        // Arrange
        Order order = new Order(1L, 1L, LocalDate.now(), 1L, Collections.emptyList(), 1L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus("PENDING");

        when(orderRepository.findByIdAndIdClientAndIdRestaurant(anyLong(), anyLong(), anyLong())).thenReturn(Optional.of(orderEntity));


        orderAdapter.deleteOrder(order);


        verify(orderRepository).save(orderEntity);
        assertEquals("CANCELLED", orderEntity.getStatus());
    }

    @Test
    void testDeleteOrder_StatusNotPending() {
        // Arrange
        Order order = new Order(1L, 1L, LocalDate.now(), 1L, Collections.emptyList(), 1L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus("READY");

        when(orderRepository.findByIdAndIdClientAndIdRestaurant(anyLong(), anyLong(), anyLong())).thenReturn(Optional.of(orderEntity));
        when(clientService.getPhoneNumber(anyLong())).thenReturn("1234567890");

        // Act
        orderAdapter.deleteOrder(order);

        // Assert
        verify(orderRepository).save(orderEntity);
        verify(clientService).sendSms(any(AddSmsSenderRequest.class));
        assertEquals("READY", orderEntity.getStatus());
    }

    @Test
    void testUpdateOrder_Transactional() {
        // Arrange
        DishQuantify dishQuantify = new DishQuantify(1L,1);
        Order order = new Order(1L,1L, LocalDate.now(),1L, Collections.singletonList(dishQuantify),1L);
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus("READY");

        when(orderRepository.findByIdAndIdClientAndIdRestaurant(anyLong(), anyLong(), anyLong())).thenReturn(Optional.of(orderEntity));
        when(clientService.getPhoneNumber(anyLong())).thenReturn("1234567890");
        doThrow(new RuntimeException()).when(clientService).sendSms(any(AddSmsSenderRequest.class));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> orderAdapter.updateOrder(order));

        verify(orderRepository, never()).save(any(OrderEntity.class));
        verify(traceabilityServicePort, never()).saveTraceability(any(Traceability.class));
    }

}