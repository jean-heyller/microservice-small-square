package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

 class AddDishRequestTest {

    private Validator validator;

    @BeforeEach
    public void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
     void testValidAddDishRequest() {
        AddDishRequest addDishRequest = new AddDishRequest("Test Dish", "Test Description", 10.0f, "Test URL", "Test Category", 1L);
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(addDishRequest);
        assertEquals(0, violations.size());
    }

    @Test
     void testInvalidAddDishRequest() {
        AddDishRequest addDishRequest = new AddDishRequest("", "", 0.0f, "", "", null);
        Set<ConstraintViolation<AddDishRequest>> violations = validator.validate(addDishRequest);
        assertEquals(7, violations.size());
    }
}