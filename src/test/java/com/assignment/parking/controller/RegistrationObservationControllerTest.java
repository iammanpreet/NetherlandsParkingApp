package com.assignment.parking.controller;

import com.assignment.parking.BaseTest;
import com.assignment.parking.model.request.RegistrationObservationRequest;
import com.assignment.parking.model.response.BaseResponse;
import com.assignment.parking.service.RegistrationObservationService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RegistrationObservationControllerTest extends BaseTest {

    @Mock
    private RegistrationObservationService registrationObservationService;

    @InjectMocks
    private RegistrationObservationController registrationObservationController;

    @Test
    public void testSaveRegistrationObservations() {
        RegistrationObservationRequest observationRequest = new RegistrationObservationRequest("License1","Street1", LocalDateTime.now());
        ResponseEntity<BaseResponse> responseEntity = registrationObservationController.saveRegistrationObservations(Arrays.asList(observationRequest));
        verify(registrationObservationService, times(1)).saveRegistrationObservations(Arrays.asList(observationRequest));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Registrations Observations saved successfully.", responseEntity.getBody().getMessage());
    }
}
