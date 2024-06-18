package com.example.microservice_small_square.adapters.driven.driving.http.mapper.request;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddTraceabilityRequest;
import com.example.microservice_small_square.domain.model.Order;
import com.example.microservice_small_square.domain.model.Traceability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ITraceabilityRequestMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "idClient", source = "idClient")
    @Mapping(target = "emailClient", source = "emailClient")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "statusBefore", source = "statusBefore")
    @Mapping(target = "statusAfter", source = "statusAfter")
    @Mapping(target = "idEmployee", source = "idEmployee")
    @Mapping(target = "emailEmployee", source = "emailEmployee")
    Traceability toModel(AddTraceabilityRequest traceabilityRequest);


    @Mapping(target = "idOrder", source = "id")
    @Mapping(target = "idClient", source = "idClient")
    @Mapping(target = "idEmployee", source = "idChef")
    Traceability toModelOrder(Order order);






}
