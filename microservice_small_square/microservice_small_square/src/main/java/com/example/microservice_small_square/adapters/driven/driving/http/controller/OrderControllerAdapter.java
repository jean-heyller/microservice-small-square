package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddOrderRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.IOrderRequestMapper;
import com.example.microservice_small_square.domain.api.IOrderServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
public class OrderControllerAdapter {
    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;


    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddOrderRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid order data supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content) })
   @PostMapping("/")
    ResponseEntity<Void> addOrder(@Valid  @RequestBody AddOrderRequest addOrderRequest){
       orderServicePort.saveOrder(orderRequestMapper.addRequestToOrder(addOrderRequest));
       return ResponseEntity.status(HttpStatus.CREATED).build();
   }

}
