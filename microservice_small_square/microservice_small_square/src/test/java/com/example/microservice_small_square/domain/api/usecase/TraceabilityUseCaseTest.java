package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.model.Traceability;
import com.example.microservice_small_square.domain.spi.ITraceabilityPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TraceabilityUseCaseTest {


    private ITraceabilityPersistencePort traceabilityPersistencePort;
    private TraceabilityUseCase traceabilityUseCase;

    @BeforeEach
    public void setup() {
        traceabilityPersistencePort = Mockito.mock(ITraceabilityPersistencePort.class);
        traceabilityUseCase = new TraceabilityUseCase(traceabilityPersistencePort);
    }

    @Test
     void testSaveTraceability() {
         Traceability traceability = new Traceability(1L, "id_client", "emailClient", LocalDate.now(), "statusBefore", "statusAfter", 1L, "emailEmployee", 1L, "status");
        traceabilityUseCase.saveTraceability(traceability);

        verify(traceabilityPersistencePort, times(1)).saveTraceability(traceability);
    }

    @Test
     void testGetTraceability() {
        Long idOrder = 1L;
        Traceability traceability1 = new Traceability(1L, "id_client1", "emailClient1", LocalDate.now(), "statusBefore1", "statusAfter1", 1L, "emailEmployee1", idOrder, "status1");
        Traceability traceability2 = new Traceability(2L, "id_client2", "emailClient2", LocalDate.now(), "statusBefore2", "statusAfter2", 2L, "emailEmployee2", idOrder, "status2");
        List<Traceability> expectedTraceabilities = Arrays.asList(traceability1, traceability2);

        when(traceabilityPersistencePort.getTraceability(idOrder)).thenReturn(expectedTraceabilities);

        List<Traceability> actualTraceabilities = traceabilityUseCase.getTraceability(idOrder);

        assertEquals(expectedTraceabilities, actualTraceabilities);
        verify(traceabilityPersistencePort, times(1)).getTraceability(idOrder);
    }

}