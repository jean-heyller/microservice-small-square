package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;


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

import com.example.microservice_small_square.adapters.driven.mongodb.adapter.TraceabilityMongodbAdapter;
import com.example.microservice_small_square.adapters.driven.mongodb.mapper.ITraceabilityEntityMapper;
import com.example.microservice_small_square.adapters.driven.sms.SmMService;
import com.example.microservice_small_square.adapters.driven.utils.enums.OrderStatus;
import com.example.microservice_small_square.adapters.driven.utils.services.RoleValidationService;
import com.example.microservice_small_square.domain.api.ITraceabilityServicePort;
import com.example.microservice_small_square.domain.model.DishQuantify;
import com.example.microservice_small_square.domain.model.Order;
import com.example.microservice_small_square.domain.model.SmsSender;
import com.example.microservice_small_square.domain.model.Traceability;
import com.example.microservice_small_square.domain.spi.IOrderPersistencePort;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

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

    private final SmMService smmService;

    private final RoleValidationService roleValidationService;

    private static final String ORDER_ERROR_MESSAGE = "Your order can't be cancelled. It's already in preparation.r";


    private final ITraceabilityRequestMapper traceabilityRequestMapper;

    private final ITraceabilityServicePort traceabilityMongodbAdapter;




    @Override
    public void saveOrder(Order order) {

        ArrayList<String> allowed = new ArrayList<>(Arrays.asList(STATUS_PENDING, "READY", "PREPARATION"));

        List<OrderEntity> existingOrders = orderRepository.findByIdClientAndStatusIn(order.getIdClient(), allowed);
        if (!existingOrders.isEmpty()) {
            throw new OrderStateUpdateException();
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
    @Transactional
    public void updateOrder(Order order) {
        OrderEntity orderEntity = orderRepository.findByIdAndIdClientAndIdRestaurant(order.getId(), order.getIdClient(), order.getIdRestaurant())
                .orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE));
        OrderStatus currentStatus = OrderStatus.valueOf(orderEntity.getStatus());
        OrderStatus nextStatus = currentStatus.next();

        OrderStatus previousStatus = currentStatus.previous();

        if (nextStatus == null) {
            throw new OrderStateUpdateException();
        }

        if (nextStatus.name().equals("READY")) {
            String number = roleValidationService.getPhoneNumber(order.getIdClient());
            String message = smmService.retrieveMessage(number);
            if (message == null) {
                throw new NoMessagesFoundException();
            }
        }

        Traceability traceability = traceabilityRequestMapper.toModelOrder(order);

        String emailEmployee = roleValidationService.getEmail(order.getIdChef());
        String emailClient = roleValidationService.getEmail(order.getIdClient());

        traceability.setEmailClient(emailClient);
        traceability.setEmailEmployee(emailEmployee);

        traceability.setStatus(currentStatus.name());
        traceability.setStatusBefore(previousStatus.name());
        traceability.setStatusAfter(nextStatus.name());

        orderEntity.setStatus(nextStatus.name());
        orderRepository.save(orderEntity);

        traceabilityMongodbAdapter.saveTraceability(traceability);
    }

    @Override
    public void deleteOrder(Order order) {
        String STATUS_CANCELLED = "CANCELLED";
        String PHONE_NUMBER_PREFIX = "+57";
        OrderEntity orderEntity = orderRepository.findByIdAndIdClientAndIdRestaurant(order.getId(),
                        order.getIdClient(), order.getIdRestaurant())
                .orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE));
        String status = orderEntity.getStatus();
        if (status.equals(STATUS_PENDING)){
            orderEntity.setStatus(STATUS_CANCELLED);
        }else{
            String number = roleValidationService.getPhoneNumber(order.getIdClient());
            String numberWithPrefix = PHONE_NUMBER_PREFIX + number;
            SmsSender smsSender = new SmsSender(numberWithPrefix, ORDER_ERROR_MESSAGE);
            smmService.sendSms(smsSender);
        }
        orderRepository.save(orderEntity);
    }

}
