package com.example.microservice_small_square.adapters.driven.mongodb.mapper;

import com.example.microservice_small_square.adapters.driven.mongodb.entity.TraceabilityEntity;

import com.example.microservice_small_square.domain.model.Traceability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface ITraceabilityEntityMapper {
    @Mapping(target = "id", ignore = true)
    Traceability toModel(TraceabilityEntity traceabilityEntity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date",ignore = true)
    @Mapping(target = "idClient", source = "idClient")
    @Mapping(target = "emailClient", source = "emailClient")
    @Mapping(target = "statusBefore", source = "statusBefore")
    @Mapping(target = "statusAfter", source = "statusAfter")
    @Mapping(target = "idEmployee", source = "idEmployee")
    @Mapping(target = "emailEmployee", source = "emailEmployee")
    TraceabilityEntity toEntity(Traceability traceability);



}
