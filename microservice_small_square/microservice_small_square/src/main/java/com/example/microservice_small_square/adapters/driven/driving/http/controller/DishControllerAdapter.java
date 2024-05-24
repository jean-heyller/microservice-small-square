package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddDishUpdapteRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.DishResponse;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.IDishRequestMapper;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.response.IDishResponseMapper;
import com.example.microservice_small_square.domain.api.IDishServicePort;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
@Validated
public class DishControllerAdapter {

    private final IDishServicePort dishServicePort;
    private final IDishRequestMapper dishRequestMapper;

    private final IDishResponseMapper dishResponseMapper;

    @Operation(summary = "Add a new dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dish created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AddDishRequest.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid dish data supplied",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dish already exists",
                    content = @Content) })
    @PreAuthorize("hasRole('OWNER')")
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
    @PatchMapping("/update")
    public ResponseEntity<Void> updateDish(@Valid @RequestBody AddDishUpdapteRequest request){
        dishServicePort.updateDish(request.getId(), Optional.ofNullable(request.getPrice()), Optional.ofNullable(request.getDescription()), request.getRestaurantId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @Operation(summary = "Change the status of a dish")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dish status updated",
                    content = { @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", description = "Invalid dish id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Dish not found",
                    content = @Content),
            @ApiResponse(responseCode = "403", description = "Permission denied",
                    content = @Content)
    })
    @PreAuthorize("hasRole('OWNER')")
    @PatchMapping("/status")
    public ResponseEntity<Void> changeStatus(@RequestParam Long id, @RequestParam Long restaurantId){
        dishServicePort.changeStatus(id, restaurantId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Operation(summary = "Get all dishes", description = "Get all dishes with optional category filtering and pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = DishResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid parameters supplied",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content) })
    @GetMapping("/all")
    public ResponseEntity<List<DishResponse>> getAllDishes(@RequestParam(defaultValue = "0") Integer page,
                                                           @RequestParam(defaultValue = "10") Integer size,
                                                           @RequestParam(required = false) String category){
        return ResponseEntity.ok(dishResponseMapper.toDishResponseList(dishServicePort.getAllDishes(page, size, category)));
    }


}
