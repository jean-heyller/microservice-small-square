package com.example.microservice_small_square.adapters.driven.client;

import com.example.microservice_small_square.configuration.client.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;



@FeignClient(name = "user-service", url = "http://localhost:8091", configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping(value = "/user/getName", consumes = MediaType.APPLICATION_JSON_VALUE)
    String getUserRoles(@RequestParam("id") Long id);
}
