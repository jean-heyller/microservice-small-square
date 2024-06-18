package com.example.microservice_small_square.domain.api.usecase;

import com.example.microservice_small_square.domain.api.ITraceabilityServicePort;
import com.example.microservice_small_square.domain.model.Traceability;
import com.example.microservice_small_square.domain.spi.ITraceabilityPersistencePort;

import java.util.List;
import java.util.Map;

public class TraceabilityUseCase implements ITraceabilityServicePort {

    private ITraceabilityPersistencePort traceabilityPersistencePort;

    public TraceabilityUseCase(ITraceabilityPersistencePort traceabilityPersistencePort) {
        this.traceabilityPersistencePort = traceabilityPersistencePort;
    }

    @Override
    public void saveTraceability(Traceability traceability) {
        traceabilityPersistencePort.saveTraceability(traceability);
    }

    @Override
    public List<Traceability> getTraceability(Long idOrder) {
        return traceabilityPersistencePort.getTraceability(idOrder);
    }

    @Override
    public Map<String, Double> getOrderProcessingTimes() {
        return traceabilityPersistencePort.getOrderProcessingTimes();
    }


}
