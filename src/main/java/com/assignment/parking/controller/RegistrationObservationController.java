package com.assignment.parking.controller;

import com.assignment.parking.model.request.RegistrationObservationRequest;
import com.assignment.parking.model.response.BaseResponse;
import com.assignment.parking.service.RegistrationObservationService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/observation")
@Validated
public class RegistrationObservationController {
    private final RegistrationObservationService registrationObservationService;
    private static final Logger logger = LoggerFactory.getLogger(RegistrationObservationController.class);

    @Autowired
    public RegistrationObservationController(RegistrationObservationService registrationObservationService) {
        this.registrationObservationService = registrationObservationService;
    }

    @Operation(summary = "Saves the registration observations")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registrations Observations saved started successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    @PostMapping(value= "/saveRegistrationObservations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BaseResponse> saveRegistrationObservations(@RequestBody @Valid List<RegistrationObservationRequest> observations) {
        registrationObservationService.saveRegistrationObservations(observations);
        logger.info("Registrations observations successfully registered size: {}", observations.size());
        return ResponseEntity.ok().body(new BaseResponse("Registrations Observations saved successfully."));
    }
}
