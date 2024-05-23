package com.example.microservice_small_square.configuration;

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
import com.example.microservice_small_square.adapters.driven.utils.services.RoleValidationService;
import com.example.microservice_small_square.domain.api.IDishServicePort;
import com.example.microservice_small_square.domain.api.IOrderServicePort;
import com.example.microservice_small_square.domain.api.IRestaurantServicePort;
import com.example.microservice_small_square.domain.api.usecase.DishUseCase;
import com.example.microservice_small_square.domain.api.usecase.OrderUseCase;
import com.example.microservice_small_square.domain.api.usecase.RestaurantUseCase;
import com.example.microservice_small_square.domain.spi.IDishPersistencePort;
import com.example.microservice_small_square.domain.spi.IOrderPersistencePort;
import com.example.microservice_small_square.domain.spi.IRestaurantPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IRestaurantEntityMapper restaurantEntityMapper;

    private final IRestaurantRepository restaurantRepository;

    private final RoleValidationService roleValidationService;

    private final IDishEntityMapper dishEntityMapper;

    private final IDishRepository dishRepository;

    private final SecurityService securityService;


    private final IOrderRepository orderRepository;

    private final IOrderEntityMapper orderEntityMapper;

    @Bean
    public IOrderPersistencePort orderPersistencePort(){
        return new OrderAdapter(dishRepository,dishEntityMapper,orderRepository,orderEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort(){

        return new OrderUseCase(orderPersistencePort());
    }

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantAdapter(restaurantRepository,restaurantEntityMapper,roleValidationService);
    }
    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort());
    }

    @Bean
    public IDishPersistencePort dishPersistencePort(){
        return new DishAdapter(dishRepository,dishEntityMapper,restaurantRepository,restaurantEntityMapper,
                roleValidationService,securityService);
    }

    @Bean
    public IDishServicePort dishServicePort(){
        return new DishUseCase(dishPersistencePort());
    }




}
