package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishUpdapteRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.IDishRequestMapper;
import com.example.microservice_small_square.domain.api.IDishServicePort;
import com.example.microservice_small_square.domain.model.Dish;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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


    @Operation(summary = "Update a dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddDishUpdapteRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid dish data supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Dish not found",
                    content = @Content) })
    @PostMapping("/update")
    public ResponseEntity<Void> updateDish( @RequestBody @Valid AddDishUpdapteRequest request){
        dishServicePort.updateDish(request.getId(), Optional.of(request.getPrice()), Optional.of(request.getDescription()));
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
