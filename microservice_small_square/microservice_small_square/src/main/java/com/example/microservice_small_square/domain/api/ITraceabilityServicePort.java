package com.example.microservice_small_square.domain.api;

import com.example.microservice_small_square.domain.model.Traceability;

import java.util.List;
import java.util.Map;

public interface ITraceabilityServicePort {
    public void saveTraceability(Traceability traceability);

    public List<Traceability> getTraceability(Long idOrder);

    Map<String, Double> getOrderProcessingTimes();


}
