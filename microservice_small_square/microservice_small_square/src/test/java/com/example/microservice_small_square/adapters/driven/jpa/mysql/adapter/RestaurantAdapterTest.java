package com.example.microservice_small_square.adapters.driven.jpa.mysql.adapter;

import com.example.microservice_small_square.adapters.driven.jpa.mysql.exceptions.PermissionDeniedException;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.mapper.IRestaurantEntityMapper;
import com.example.microservice_small_square.adapters.driven.jpa.mysql.repository.IRestaurantRepository;
import com.example.microservice_small_square.adapters.driven.utils.services.RoleValidationService;
import com.example.microservice_small_square.domain.model.Restaurant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

 class RestaurantAdapterTest {
    private IRestaurantRepository repository;
    private IRestaurantEntityMapper mapper;
    private RoleValidationService roleValidationService;
    private RestaurantAdapter adapter;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(IRestaurantRepository.class);
        mapper = Mockito.mock(IRestaurantEntityMapper.class);
        roleValidationService = Mockito.mock(RoleValidationService.class);
        adapter = new RestaurantAdapter(repository, mapper, roleValidationService);
    }



    @Test
     void testSaveRestaurant_WithInvalidRole() {
        Restaurant restaurant = new Restaurant(1L, "Test Name", "Test Nit", "Test Address", "Test Phone", "Test URL", 1L);
        when(roleValidationService.validateUserRole(restaurant.getOwnerId(), "owner")).thenReturn(false);

        assertThrows(PermissionDeniedException.class, () -> adapter.saveRestaurant(restaurant));
    }
}