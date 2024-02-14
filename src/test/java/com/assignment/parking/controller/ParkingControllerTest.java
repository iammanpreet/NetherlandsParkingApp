package com.assignment.parking.controller;

import com.assignment.parking.BaseTest;
import com.assignment.parking.model.request.UnregisterParkingRequest;
import com.assignment.parking.model.response.BaseResponse;
import com.assignment.parking.service.ParkingService;
import com.assignment.parking.model.request.RegisterParkingRequest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ParkingControllerTest extends BaseTest {

    @Mock
    private ParkingService parkingService;

    @InjectMocks
    private ParkingController parkingController;

    @Test
    public void testRegisterParkingSession() {
        RegisterParkingRequest registerParkingRequest = new RegisterParkingRequest("License1","Street1");
        ResponseEntity<BaseResponse> responseEntity = parkingController.registerParkingSession(registerParkingRequest);
        verify(parkingService, times(1)).registerParkingSession(registerParkingRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Parking session started successfully for license plate number: License1", responseEntity.getBody().getMessage());
    }

    @Test
    public void testUnregisterParkingSession() {
        UnregisterParkingRequest unregisterParkingRequest = new UnregisterParkingRequest("License1");

        ResponseEntity<BaseResponse> responseEntity = parkingController.unregisterParkingSession(unregisterParkingRequest);
        verify(parkingService, times(1)).unregisterParkingSession(unregisterParkingRequest);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Parking session ended successfully for license plate number: License1", responseEntity.getBody().getMessage());
    }
}
