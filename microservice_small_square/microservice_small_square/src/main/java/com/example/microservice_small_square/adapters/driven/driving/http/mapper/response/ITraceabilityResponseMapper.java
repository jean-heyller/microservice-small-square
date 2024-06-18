package com.example.microservice_small_square.adapters.driven.driving.http.mapper.response;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.TraceabilityResponse;
import com.example.microservice_small_square.domain.model.Traceability;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ITraceabilityResponseMapper {

    List<TraceabilityResponse> toTraceabilityResponseList(List<Traceability> traceabilityList);
}
