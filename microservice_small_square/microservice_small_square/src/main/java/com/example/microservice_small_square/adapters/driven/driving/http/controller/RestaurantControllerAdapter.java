package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddRestaurantRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.IRestaurantRequestMapper;
import com.example.microservice_small_square.domain.api.IRestaurantServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
@Validated
public class RestaurantControllerAdapter {
    private final IRestaurantRequestMapper restaurantRequestMapper;

    private final IRestaurantServicePort restaurantServicePort;

    @Operation(summary = "Add a new restaurant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Restaurant created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddRestaurantRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid restaurant data supplied",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Restaurant already exists",
                    content = @Content) })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<Void> addRestaurant(@Valid @RequestBody AddRestaurantRequest request){
        restaurantServicePort.saveRestaurant(restaurantRequestMapper.addRequestToRestaurant(request));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
