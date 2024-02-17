package com.assignment.parking.exception;
import com.assignment.parking.model.response.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleNotFoundException() {
        NotFoundException ex = new NotFoundException("Not found");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleNotFoundException(ex);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertErrorResponse(responseEntity.getBody(), HttpStatus.NOT_FOUND.value(), "Not found");
    }

    @Test
    void handleVehicleAlreadyParkedException() {
        VehicleAlreadyParkedException ex = new VehicleAlreadyParkedException("Vehicle already parked");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleVehicleAlreadyParkedException(ex);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertErrorResponse(responseEntity.getBody(), HttpStatus.CONFLICT.value(), "Vehicle already parked");
    }

    @Test
    void handleException() {
        Exception ex = new Exception("Internal server error");
        ResponseEntity<ErrorResponse> responseEntity = globalExceptionHandler.handleException(ex);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertErrorResponse(responseEntity.getBody(), HttpStatus.INTERNAL_SERVER_ERROR.value(), "Internal server error");
    }

    private void assertErrorResponse(ErrorResponse errorResponse, int expectedStatus, String expectedMessage) {
        assertEquals(expectedStatus, errorResponse.getStatus());
        assertEquals(expectedMessage, errorResponse.getMessage());
        assertEquals(LocalDateTime.class, errorResponse.getTimestamp().getClass());
    }
}
