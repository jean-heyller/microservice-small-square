package com.example.microservice_small_square.adapters.driven.mongodb.adapter;

import com.example.microservice_small_square.adapters.driven.mongodb.entity.TraceabilityEntity;
import com.example.microservice_small_square.adapters.driven.mongodb.mapper.ITraceabilityEntityMapper;
import com.example.microservice_small_square.adapters.driven.mongodb.repository.ITraceabilityRepository;
import com.example.microservice_small_square.domain.model.Traceability;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

class TraceabilityMongodbAdapterTest {
    @InjectMocks
    private TraceabilityMongodbAdapter traceabilityMongodbAdapter;

    @Mock
    private ITraceabilityRepository traceabilityRepository;

    @Mock
    private ITraceabilityEntityMapper traceabilityEntityMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testSaveTraceability() {
        // Arrange
        Traceability traceability = new Traceability(1L, "id_client", "emailClient", LocalDate.now(), "statusBefore", "statusAfter", 1L, "emailEmployee", 1L, "status");
        TraceabilityEntity traceabilityEntity = new TraceabilityEntity();
        traceabilityEntity.setId("1");
        traceabilityEntity.setIdOrder("1");
        traceabilityEntity.setIdClient("id_client");
        traceabilityEntity.setEmailClient("emailClient");
        traceabilityEntity.setDate(LocalDate.now());
        traceabilityEntity.setStatus("status");
        traceabilityEntity.setStatusBefore("statusBefore");
        traceabilityEntity.setStatusAfter("statusAfter");
        traceabilityEntity.setIdEmployee("1");
        traceabilityEntity.setEmailEmployee("emailEmployee");

        when(traceabilityEntityMapper.toEntity(traceability)).thenReturn(traceabilityEntity);

        traceabilityMongodbAdapter.saveTraceability(traceability);

        verify(traceabilityRepository, times(1)).save(traceabilityEntity);
    }


    @Test
     void testGetTraceability() {
        // Arrange
        Long idOrder = 1L;
        TraceabilityEntity traceabilityEntity = new TraceabilityEntity();
        traceabilityEntity.setId("1");
        traceabilityEntity.setIdOrder("1");
        traceabilityEntity.setIdClient("id_client");
        traceabilityEntity.setEmailClient("emailClient");
        traceabilityEntity.setDate(LocalDate.now());
        traceabilityEntity.setStatus("status");
        traceabilityEntity.setStatusBefore("statusBefore");
        traceabilityEntity.setStatusAfter("statusAfter");
        traceabilityEntity.setIdEmployee("1");
        traceabilityEntity.setEmailEmployee("emailEmployee");
        Traceability traceability = new Traceability(1L, "id_client", "emailClient", LocalDate.now(), "statusBefore", "statusAfter", 1L, "emailEmployee", 1L, "status");


        when(traceabilityRepository.findByIdOrder(idOrder)).thenReturn(Collections.singletonList(traceabilityEntity));
        when(traceabilityEntityMapper.toModel(traceabilityEntity)).thenReturn(traceability);


        List<Traceability> result = traceabilityMongodbAdapter.getTraceability(idOrder);


        assertEquals(Collections.singletonList(traceability), result);
    }

}