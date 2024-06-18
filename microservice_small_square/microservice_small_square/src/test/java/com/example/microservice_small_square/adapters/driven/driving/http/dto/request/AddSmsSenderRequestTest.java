package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class AddSmsSenderRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
     void testAddSmsSenderRequest_AllFieldsNotNull() {
        AddSmsSenderRequest request = new AddSmsSenderRequest("1234567890", "Test message");
        Set<ConstraintViolation<AddSmsSenderRequest>> violations = validator.validate(request);
        assertEquals(0, violations.size());
    }

    @Test
     void testAddSmsSenderRequest_ToNull() {
        AddSmsSenderRequest request = new AddSmsSenderRequest(null, "Test message");
        Set<ConstraintViolation<AddSmsSenderRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("The field 'from' is mandatory", violations.iterator().next().getMessage());
    }

    @Test
     void testAddSmsSenderRequest_MessageNull() {
        AddSmsSenderRequest request = new AddSmsSenderRequest("1234567890", null);
        Set<ConstraintViolation<AddSmsSenderRequest>> violations = validator.validate(request);
        assertEquals(1, violations.size());
        assertEquals("The field 'message' is mandatory", violations.iterator().next().getMessage());
    }


}