package com.example.microservice_small_square.adapters.driven.mongodb.repository;

import com.example.microservice_small_square.adapters.driven.mongodb.entity.TraceabilityEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ITraceabilityRepository extends MongoRepository<TraceabilityEntity, Long> {
    List<TraceabilityEntity> findByIdOrder(Long idOrder);


}
