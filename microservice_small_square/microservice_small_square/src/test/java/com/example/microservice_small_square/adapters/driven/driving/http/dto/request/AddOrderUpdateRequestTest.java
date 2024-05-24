package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;



import com.example.microservice_small_square.adapters.driven.driving.http.util.AdapterConstans;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddOrderUpdateRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void testAddOrderUpdateRequest_AllFieldsNotNull() {
        AddOrderUpdateRequest request = new AddOrderUpdateRequest(1L, 1L, 1L, 1L);
        Set<ConstraintViolation<AddOrderUpdateRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
    public void testAddOrderUpdateRequest_IdRestaurantNull() {
        AddOrderUpdateRequest request = new AddOrderUpdateRequest(null, 1L, 1L, 1L);
        Set<ConstraintViolation<AddOrderUpdateRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals(AdapterConstans.FIELD_RESTAURANT_ID_NULL_MESSAGE, violations.iterator().next().getMessage());
    }


}