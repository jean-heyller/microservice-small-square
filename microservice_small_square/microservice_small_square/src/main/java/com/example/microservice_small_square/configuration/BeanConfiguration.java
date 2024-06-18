package com.example.microservice_small_square.configuration;

import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.ITraceabilityRequestMapper;
import com.example.microservice_small_square.adapters.driven.driving.http.util.SecurityService;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter.DishAdapter;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter.OrderAdapter;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter.RestaurantAdapter;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IDishEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IOrderEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IDishRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IOrderRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.example.microservice_small_square.adapters.driven.mongodb.adapter.TraceabilityMongodbAdapter;
import com.example.microservice_small_square.adapters.driven.mongodb.mapper.ITraceabilityEntityMapper;
import com.example.microservice_small_square.adapters.driven.mongodb.repository.ITraceabilityRepository;

import com.example.microservice_small_square.adapters.driven.utils.services.ClientService;
import com.example.microservice_small_square.domain.api.*;
import com.example.microservice_small_square.domain.api.usecase.*;
import com.example.microservice_small_square.domain.spi.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IRestaurantRepository restaurantRepository;

    private final ClientService clientService;

    private final IDishEntityMapper dishEntityMapper;

    private final IDishRepository dishRepository;

    private final SecurityService securityService;


    private final IOrderRepository orderRepository;

    private final IOrderEntityMapper orderEntityMapper;



    private final ITraceabilityRepository traceabilityRepository;

    private final ITraceabilityEntityMapper traceabilityEntityMapper;

    private final ITraceabilityRequestMapper traceabilityRequestMapper;





    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderAdapter(dishRepository,dishEntityMapper,orderRepository,orderEntityMapper,
                clientService,traceabilityRequestMapper,traceabilityServicePort());
    }

    @Bean
    public IOrderServicePort orderServicePort(){

        return new OrderUseCase(orderPersistencePort());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantAdapter(restaurantRepository,restaurantEntityMapper, clientService);
    }
    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort(){
        return new DishAdapter(dishRepository,dishEntityMapper,restaurantRepository,restaurantEntityMapper,
                clientService,securityService);
    }

    @Bean
    public IDishServicePort dishServicePort(){
        return new DishUseCase(dishPersistencePort());
    }


    @Bean
    public ITraceabilityPersistencePort traceabilityPersistencePort(){
        return new TraceabilityMongodbAdapter(traceabilityRepository,traceabilityEntityMapper);
    }

    @Bean
    public ITraceabilityServicePort traceabilityServicePort(){
        return new TraceabilityUseCase(traceabilityPersistencePort());
    }


}
