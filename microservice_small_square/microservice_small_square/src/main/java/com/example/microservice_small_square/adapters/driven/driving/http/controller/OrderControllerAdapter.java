package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddOrderRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddOrderUpdateRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.OrderResponse;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.IOrderRequestMapper;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.response.IOrderResponseMapper;
import com.example.microservice_small_square.domain.api.IOrderServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@Validated
public class OrderControllerAdapter {
    private final IOrderServicePort orderServicePort;
    private final IOrderRequestMapper orderRequestMapper;

    private final IOrderResponseMapper orderResponseMapper;


    @Operation(summary = "Add a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Order created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddOrderRequest.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid order data supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)})
    @PostMapping("/")
    ResponseEntity<Void> addOrder(@Valid @RequestBody AddOrderRequest addOrderRequest) {
        orderServicePort.saveOrder(orderRequestMapper.addRequestToOrder(addOrderRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @Operation(summary = "Get orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Orders not found",
                    content = @Content)})
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping("/")
    ResponseEntity<List<OrderResponse>> getOrder(@RequestParam Integer page, Integer size,String status,
                                                 Long idRestaurant, Long idClient) {
        return ResponseEntity.ok(orderResponseMapper.toOrderResponseList(orderServicePort.getOrders(
                page,
                size,
                status,
                idRestaurant,
                idClient
        )));
    }

    @PatchMapping("/update")
    public ResponseEntity<Void> updateOrder(@Valid @RequestBody AddOrderUpdateRequest request){
        orderServicePort.updateOrder(orderRequestMapper.addUpdateRequestToOrder(request));
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
