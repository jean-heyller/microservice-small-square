package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AddRestaurantRequestTest {

    private AddRestaurantRequest addRestaurantRequest;
    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        addRestaurantRequest = new AddRestaurantRequest("Test Name", "Test Nit", "Test Address", "Test Phone", "Test URL", 1L);
    }

    @Test
    void getName() {
        assertEquals("Test Name", addRestaurantRequest.getName());
        assertTrue(validator.validateProperty(addRestaurantRequest, "name").isEmpty());
    }



    @Test
    void getAddress() {
        assertEquals("Test Address", addRestaurantRequest.getAddress());
        assertTrue(validator.validateProperty(addRestaurantRequest, "address").isEmpty());
    }

    @Test
    void testUrlLogoValidation() {
        AddRestaurantRequest invalidRequest = new AddRestaurantRequest("Test Name", "Test Nit", "Test Address", "Test Phone", "", 1L);
        Set<ConstraintViolation<AddRestaurantRequest>> violations = validator.validate(invalidRequest);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testOwnerIdValidation() {
        AddRestaurantRequest invalidRequest = new AddRestaurantRequest("Test Name", "Test Nit", "Test Address", "Test Phone", "Test URL", null);
        Set<ConstraintViolation<AddRestaurantRequest>> violations = validator.validate(invalidRequest);
        assertFalse(violations.isEmpty());
    }

    @Test
    void testAddressValidation() {
        AddRestaurantRequest invalidRequest = new AddRestaurantRequest("Test Name", "Test Nit", "", "Test Phone", "Test URL", 1L);
        Set<ConstraintViolation<AddRestaurantRequest>> violations = validator.validate(invalidRequest);
        assertFalse(violations.isEmpty());
    }









}