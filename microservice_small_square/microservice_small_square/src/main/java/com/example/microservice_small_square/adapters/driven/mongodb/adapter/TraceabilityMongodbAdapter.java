package com.example.microservice_small_square.adapters.driven.mongodb.adapter;

import com.example.microservice_small_square.adapters.driven.mongodb.entity.TraceabilityEntity;
import com.example.microservice_small_square.adapters.driven.mongodb.mapper.ITraceabilityEntityMapper;

import com.example.microservice_small_square.adapters.driven.mongodb.repository.ITraceabilityRepository;
import com.example.microservice_small_square.domain.model.Traceability;
import com.example.microservice_small_square.domain.spi.ITraceabilityPersistencePort;
import lombok.RequiredArgsConstructor;

import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TraceabilityMongodbAdapter implements ITraceabilityPersistencePort {





    private final ITraceabilityRepository traceabilityRepository;

    private final ITraceabilityEntityMapper traceabilityEntityMapper;

    @Override
    public void saveTraceability(Traceability traceability) {
        traceabilityRepository.save(traceabilityEntityMapper.toEntity(traceability));
    }


    @Override
    public List<Traceability> getTraceability(Long idOrder) {
        List<TraceabilityEntity> traceabilityEntityList = traceabilityRepository.findByIdOrder(idOrder);
        return traceabilityEntityList.stream().map(traceabilityEntityMapper::toModel).toList();
    }


    @Override
    public Map<String, Double> getOrderProcessingTimes() {
        List<TraceabilityEntity> traceabilityEntityList = traceabilityRepository.findAll();
        Map<String, Double> orderProcessingTimes = new HashMap<>();

        for (TraceabilityEntity traceabilityEntity : traceabilityEntityList) {
            if (traceabilityEntity.getStatus().equals("PREPARATION")) {
                for (TraceabilityEntity traceabilityEntity2 : traceabilityEntityList) {
                    if (traceabilityEntity2.getStatus().equals("READY") && traceabilityEntity2.getIdOrder().equals(traceabilityEntity.getIdOrder())) {
                        long minutesBetween = ChronoUnit.MINUTES.between(
                                traceabilityEntity.getDate(),
                                traceabilityEntity2.getDate()
                        );
                        orderProcessingTimes.put(traceabilityEntity.getIdOrder(), (double) minutesBetween);
                    }
                }
            }
        }

        return orderProcessingTimes;
    }

}
