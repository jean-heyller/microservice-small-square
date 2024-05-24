package com.example.microservice_small_square.adapters.driven.driving.http.mapper.request;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddSmsSenderRequest;
import com.example.microservice_small_square.domain.model.SmsSender;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ISmsSenderRequestMapper {
    SmsSender toSmsSender(AddSmsSenderRequest addSmsSenderRequest);
}
