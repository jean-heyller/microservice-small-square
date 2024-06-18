package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddTraceabilityRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.TraceabilityResponse;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.ITraceabilityRequestMapper;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.response.ITraceabilityResponseMapper;
import com.example.microservice_small_square.domain.api.ITraceabilityServicePort;
import com.example.microservice_small_square.domain.model.Traceability;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class TraceabilityControllerAdapterTest {

    private ITraceabilityServicePort traceabilityServicePort;
    private ITraceabilityRequestMapper traceabilityRequestMapper;
    private ITraceabilityResponseMapper traceabilityResponseMapper;
    private TraceabilityControllerAdapter traceabilityControllerAdapter;

    @BeforeEach
    public void setup() {
        traceabilityServicePort = Mockito.mock(ITraceabilityServicePort.class);
        traceabilityRequestMapper = Mockito.mock(ITraceabilityRequestMapper.class);
        traceabilityResponseMapper = Mockito.mock(ITraceabilityResponseMapper.class);
        traceabilityControllerAdapter = new TraceabilityControllerAdapter(traceabilityServicePort, traceabilityRequestMapper, traceabilityResponseMapper);
    }

    @Test
     void testAddTraceability() {
        AddTraceabilityRequest addTraceabilityRequest = new AddTraceabilityRequest(1L, "id_client", "emailClient", LocalDate.now(), "statusBefore", "statusAfter", 1L, "emailEmployee");
        Traceability traceability = new Traceability(1L, "id_client", "emailClient", LocalDate.now(), "statusBefore", "statusAfter", 1L, "emailEmployee", 1L, "status");

        when(traceabilityRequestMapper.toModel(addTraceabilityRequest)).thenReturn(traceability);

        ResponseEntity<Void> responseEntity = traceabilityControllerAdapter.addTraceability(addTraceabilityRequest);

        verify(traceabilityServicePort, times(1)).saveTraceability(traceability);
        assertEquals(201, responseEntity.getStatusCodeValue());
    }

    @Test
     void testGetTraceability() {
        Long idOrder = 1L;
        Traceability traceability = new Traceability(1L, "id_client", "emailClient", LocalDate.now(), "statusBefore", "statusAfter", 1L, "emailEmployee", idOrder, "status");
        List<Traceability> traceabilityList = Arrays.asList(traceability);
        TraceabilityResponse traceabilityResponse = new TraceabilityResponse(
                "1", "id_client", "emailClient", LocalDate.now(), "statusBefore", "statusAfter", "1"
                , "emailEmployee", "PENDING"
        );
        List<TraceabilityResponse> traceabilityResponseList = Arrays.asList(traceabilityResponse);

        when(traceabilityServicePort.getTraceability(idOrder)).thenReturn(traceabilityList);
        when(traceabilityResponseMapper.toTraceabilityResponseList(traceabilityList)).thenReturn(traceabilityResponseList);

        ResponseEntity<List<TraceabilityResponse>> responseEntity = traceabilityControllerAdapter.getTraceability(idOrder);

        assertEquals(traceabilityResponseList, responseEntity.getBody());
        verify(traceabilityServicePort, times(1)).getTraceability(idOrder);
    }

}