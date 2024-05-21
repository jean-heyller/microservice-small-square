package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.IDishRequestMapper;
import com.example.microservice_small_square.domain.api.IDishServicePort;
import com.example.microservice_small_square.domain.model.Dish;
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

@RestController
@RequestMapping("/dish")
@Validated
@RequiredArgsConstructor
public class DishControllerAdapter {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;

    @Operation(summary = "Add a new dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddDishRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid dish data supplied",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dish already exists",
                    content = @Content) })
    @PostMapping("/")
     public ResponseEntity<Void> addDish(@RequestBody AddDishRequest request){
            dishServicePort.saveDish(dishRequestMapper.addRequestToDish(request));
            return ResponseEntity.status(HttpStatus.CREATED).build();
     }

}
