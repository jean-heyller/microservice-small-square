package com.example.microservice_small_square.adapters.driven.driving.http.dto.request;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AddUpdateStatusTest {

    @Test
    void testGetStatus() {
        Boolean status = true;
        Long restaurantId = 1L;
        AddUpdateStatus addUpdateStatus = new AddUpdateStatus(status, restaurantId);

        assertEquals(status, addUpdateStatus.getStatus());
    }
}