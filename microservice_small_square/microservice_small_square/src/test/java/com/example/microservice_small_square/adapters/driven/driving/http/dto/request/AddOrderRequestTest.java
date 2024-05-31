package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.personalized.AddDishType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class AddOrderRequestTest {


    private Validator validator;

    @BeforeEach
     void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
     void testAddOrderRequestValidation() {
        AddOrderRequest addOrderRequest = new AddOrderRequest(null, LocalDate.now(), 1L, Collections.singletonList(new AddDishType(1L, 1)), 1L, 1L);
        var violations = validator.validate(addOrderRequest);
        assertEquals(1, violations.size());
        assertEquals("id", violations.iterator().next().getPropertyPath().toString());
        assertEquals("`restaurant id` cannot be null", violations.iterator().next().getMessage());
    }



    @Test
     void testIdValidation() {
        AddOrderRequest addOrderRequest = new AddOrderRequest(null, LocalDate.now(), 1L, Collections.singletonList(new AddDishType(1L, 1)), 1L, 1L);
        var violations = validator.validate(addOrderRequest);
        assertEquals(1, violations.size());
        assertEquals("id", violations.iterator().next().getPropertyPath().toString());
    }



}