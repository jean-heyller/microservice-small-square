package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;


import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddSmsSenderRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.ITraceabilityRequestMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.DishEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.entity.OrderEntity;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.CodeNotValidatedException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.DataNotFoundException;


import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.OrderStateUpdateException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IOrderRepository;



import com.example.microservice_small_square.adapters.driven.utils.enums.OrderStatus;
import com.example.microservice_small_square.adapters.driven.utils.services.ClientService;
import com.example.microservice_small_square.domain.api.ITraceabilityServicePort;
import com.example.microservice_small_square.domain.model.DishQuantify;
import com.example.microservice_small_square.domain.model.Order;

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

    private static final String STATUS_PREPARATION = "PREPARATION";



    private static final String ERROR_MESSAGE = "The dish ";


    private final ClientService clientService;

    private static final String ORDER_ERROR_MESSAGE = "Your order can't be cancelled. It's already in preparation.r";


    private final ITraceabilityRequestMapper traceabilityRequestMapper;

    private final ITraceabilityServicePort traceabilityMongodbAdapter;

    private Boolean validatecode = false;


    @Override
    @Transactional
    public void saveOrder(Order order) {



        ArrayList<String> allowed = new ArrayList<>(Arrays.asList(STATUS_PENDING, "READY", STATUS_PREPARATION));

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

        Traceability traceability = traceabilityRequestMapper.toModelOrder(order);

        String emailEmployee = clientService.getEmail(order.getIdChef());
        String emailClient = clientService.getEmail(order.getIdClient());

        traceability.setEmailClient(emailClient);
        traceability.setEmailEmployee(emailEmployee);

        String currentStatus = STATUS_PENDING;

        traceability.setStatus(currentStatus);
        traceability.setStatusAfter(STATUS_PREPARATION);

        orderEntity.setDishes(dishEntitiesCopy);
        orderRepository.save(orderEntity);
        traceabilityMongodbAdapter.saveTraceability(traceability);
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



        if (nextStatus == null) {
            throw new OrderStateUpdateException();
        }

        if (currentStatus.name().equals("READY")) {
            String number = clientService.getPhoneNumber(order.getIdClient());
            String numberWithPrefix = "+57" + number;
            String message = "12345";
            AddSmsSenderRequest smsSender = new AddSmsSenderRequest(numberWithPrefix, message);

            clientService.sendSms(smsSender);
        }


        Traceability traceability = traceabilityRequestMapper.toModelOrder(order);

        String emailEmployee = clientService.getEmail(order.getIdChef());
        String emailClient = clientService.getEmail(order.getIdClient());

        traceability.setEmailClient(emailClient);
        traceability.setEmailEmployee(emailEmployee);

        if ("DELIVERED".equals(nextStatus.name()) && !orderEntity.getCodeValidated()) {
            throw new CodeNotValidatedException();
        }

        traceability.setStatus(nextStatus.name());
        OrderStatus currentStatusTraceability = OrderStatus.valueOf(nextStatus.name());
        OrderStatus nextStatusTraceability = currentStatus.next();
        traceability.setStatusBefore(currentStatusTraceability.previous().name());
        traceability.setStatusAfter(nextStatusTraceability.next().name());

        orderEntity.setStatus(nextStatus.name());
        orderRepository.save(orderEntity);

        traceabilityMongodbAdapter.saveTraceability(traceability);
    }

    @Override
    public void deleteOrder(Order order) {
        String statusCancelled = "CANCELLED";
        String phoneNumberPrefix = "+57";
        OrderEntity orderEntity = orderRepository.findByIdAndIdClientAndIdRestaurant(order.getId(),
                        order.getIdClient(), order.getIdRestaurant())
                .orElseThrow(() -> new DataNotFoundException(ERROR_MESSAGE));
        String status = orderEntity.getStatus();
        if (status.equals(STATUS_PENDING)) {
            orderEntity.setStatus(statusCancelled);
        } else {
            String number = clientService.getPhoneNumber(order.getIdClient());
            String numberWithPrefix = phoneNumberPrefix + number;
            AddSmsSenderRequest smsSender = new AddSmsSenderRequest(numberWithPrefix, "Your order can't be cancelled. It's already in preparation.");
            clientService.sendSms(smsSender);
        }
        orderRepository.save(orderEntity);
    }


    @Override
    public void validaCode(String number, String code, String idOrder) {
        String codeMessage = clientService.getMessage(number);
        if (codeMessage.equals(code)) {
            OrderEntity orderEntity = orderRepository.findById(Long.parseLong(idOrder)).orElseThrow(() -> new DataNotFoundException("Order"));
            boolean validate = true;
            orderEntity.setCodeValidated(validate);
            orderRepository.save(orderEntity);
        } else {
            throw new DataNotFoundException("Code is not valid");
        }
    }

}
