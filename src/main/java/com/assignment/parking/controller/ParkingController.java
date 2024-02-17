package com.assignment.parking.controller;

import com.assignment.parking.model.request.UnregisterParkingRequest;
import com.assignment.parking.model.response.BaseResponse;
import com.assignment.parking.model.response.UnregisterParkingResponse;
import com.assignment.parking.service.ParkingService;
import com.assignment.parking.model.request.RegisterParkingRequest;
import com.assignment.parking.service.impl.ParkingServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parking")
@Validated
public class ParkingController {
    private final ParkingService parkingService;
    private static final Logger logger = LoggerFactory.getLogger(ParkingController.class);

    @Autowired
    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @Operation(summary = "Registers a parking session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parking session started successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping(value = "/registerParkingSession", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> registerParkingSession(@RequestBody @Valid RegisterParkingRequest registerParkingRequest) {
        parkingService.registerParkingSession(registerParkingRequest);
        logger.info("Parking successfully registered for license Plate: {}", registerParkingRequest.getLicensePlateNumber());
        return ResponseEntity.ok().body(new BaseResponse(String.format("Parking session started successfully for license plate number: %s", registerParkingRequest.getLicensePlateNumber())));
    }

    @Operation(summary = "Unregisters or end a parking session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Parking session ended successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping(value = "/unregisterParkingSession", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UnregisterParkingResponse> unregisterParkingSession(@RequestBody @Valid UnregisterParkingRequest unregisterParkingRequest) {
        UnregisterParkingResponse unregisterParkingResponse = parkingService.unregisterParkingSession(unregisterParkingRequest);
        logger.info("Parking successfully unregistered for license Plate: {}", unregisterParkingRequest.getLicensePlateNumber());
        return ResponseEntity.ok().body(unregisterParkingResponse);
    }
}
