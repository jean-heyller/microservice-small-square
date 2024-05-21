package com.example.microservice_small_square.configuration;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter.RestaurantAdapter;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.utils.RoleValidationService;
import com.example.microservice_small_square.domain.api.IRestaurantServicePort;
import com.example.microservice_small_square.domain.api.usecase.RestaurantUseCase;
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

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort(){
        return new RestaurantAdapter(restaurantRepository,restaurantEntityMapper,roleValidationService);
    }
    @Bean
    public IRestaurantServicePort restaurantServicePort(){
        return new RestaurantUseCase(restaurantPersistencePort());
    }

}
