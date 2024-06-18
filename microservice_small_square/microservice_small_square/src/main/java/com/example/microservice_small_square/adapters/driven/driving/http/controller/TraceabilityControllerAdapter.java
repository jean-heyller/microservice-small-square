package com.example.microservice_small_square.adapters.driven.driving.http.controller;

import com.example.microservice_small_square.adapters.driven.driving.http.dto.request.AddTraceabilityRequest;
import com.example.microservice_small_square.adapters.driven.driving.http.dto.response.TraceabilityResponse;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.request.ITraceabilityRequestMapper;
import com.example.microservice_small_square.adapters.driven.driving.http.mapper.response.ITraceabilityResponseMapper;
import com.example.microservice_small_square.domain.api.ITraceabilityServicePort;
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
import java.util.Map;

@RestController
@RequestMapping("/traceability")
@RequiredArgsConstructor
@Validated
public class TraceabilityControllerAdapter {

    private final ITraceabilityServicePort traceabilityServicePort;
    private final ITraceabilityRequestMapper traceabilityRequestMapper;

    private final ITraceabilityResponseMapper traceabilityResponseMapper;


    @PostMapping("/add")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Add traceability")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Traceability added",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content) })
    public ResponseEntity<Void> addTraceability(@Valid @RequestBody AddTraceabilityRequest addTraceabilityRequest) {
        traceabilityServicePort.saveTraceability(traceabilityRequestMapper.toModel(addTraceabilityRequest));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/get")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get traceability by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Traceability found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TraceabilityResponse.class)) }),
            @ApiResponse(responseCode = "404", description = "Traceability not found",
                    content = @Content) })
    public ResponseEntity<List<TraceabilityResponse>> getTraceability(@RequestParam Long id) {
        return ResponseEntity.ok(traceabilityResponseMapper.toTraceabilityResponseList(traceabilityServicePort.getTraceability(id)));
    }

    @GetMapping("/getProcessingTimes")
    @PreAuthorize("permitAll()")
    @Operation(summary = "Get processing times")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Processing times found",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Map.class)) }),
            @ApiResponse(responseCode = "404", description = "Processing times not found",
                    content = @Content) })
    public ResponseEntity<Map<String, Double>> getProcessingTimes() {
        return ResponseEntity.ok(traceabilityServicePort.getOrderProcessingTimes());
    }

}
