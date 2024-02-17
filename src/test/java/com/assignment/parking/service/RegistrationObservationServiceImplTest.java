package com.assignment.parking.service;

import com.assignment.parking.BaseTest;
import com.assignment.parking.exception.NotFoundException;
import com.assignment.parking.model.RegistrationObservationRecord;
import com.assignment.parking.model.Street;
import com.assignment.parking.model.request.RegistrationObservationRequest;
import com.assignment.parking.repository.RegistrationObservationRepository;
import com.assignment.parking.service.impl.RegistrationObservationServiceImpl;
import com.assignment.parking.model.response.ParkingRegistrationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class RegistrationObservationServiceImplTest extends BaseTest {

    @Mock
    private RegistrationObservationRepository registrationObservationRepository;

    @Mock
    private ParkingService parkingService;

    @Mock
    private StreetService streetService;

    @Mock
    private UnregisteredLicensePlateService unregisteredLicensePlateService;

    @InjectMocks
    private RegistrationObservationServiceImpl registrationObservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveRegistrationObservations_WithParkingExists() {
        List<RegistrationObservationRequest> observationRequests = createObservationRequests();
        when(registrationObservationRepository.saveAll(anyList())).thenReturn(createObservationRecords(observationRequests));
        when(parkingService.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(any()))
                .thenReturn(Optional.of(createParkingRegistrationResponse(observationRequests.get(0))));
        Street street = new Street();
        when(streetService.getStreetByName(anyString())).thenReturn(Optional.of(street));

        assertDoesNotThrow(() -> registrationObservationService.saveRegistrationObservations(observationRequests));

        verify(registrationObservationRepository, times(1)).saveAll(anyList());
        verify(unregisteredLicensePlateService, never()).saveUnregisteredLicensePlate(any());
    }

    @Test
    void testSaveRegistrationObservations_WithUnregisteredPlate() {
        List<RegistrationObservationRequest> observationRequests = createObservationRequests();
        when(registrationObservationRepository.saveAll(anyList())).thenReturn(createObservationRecords(observationRequests));
        when(parkingService.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(any()))
                .thenReturn(Optional.empty());
        Street street = new Street();
        when(streetService.getStreetByName(anyString())).thenReturn(Optional.of(street));

        assertDoesNotThrow(() -> registrationObservationService.saveRegistrationObservations(observationRequests));

        verify(registrationObservationRepository, times(1)).saveAll(anyList());
        verify(unregisteredLicensePlateService, times(2)).saveUnregisteredLicensePlate(any());
    }

    private List<RegistrationObservationRequest> createObservationRequests() {
        List<RegistrationObservationRequest> observationRequests = new ArrayList<>();
        observationRequests.add(new RegistrationObservationRequest("License1", "Street1", LocalDateTime.now()));
        observationRequests.add(new RegistrationObservationRequest("License2", "Street2", LocalDateTime.now()));
        return observationRequests;
    }

    private List<RegistrationObservationRecord> createObservationRecords(List<RegistrationObservationRequest> observationRequests) {
        List<RegistrationObservationRecord> observationRecords = new ArrayList<>();
        for (RegistrationObservationRequest request : observationRequests) {
            RegistrationObservationRecord record = new RegistrationObservationRecord();
            record.setObservationDate(request.getObservationDate());
            record.setLicensePlateNumber(request.getLicensePlateNumber());
            observationRecords.add(record);
        }
        return observationRecords;
    }
    @Test
    void testSaveRegistrationObservations_StreetNotFound() {
        List<RegistrationObservationRequest> observationRequests = new ArrayList<>();
        observationRequests.add(new RegistrationObservationRequest("License1", "Street1", LocalDateTime.now()));
        when(streetService.getStreetByName(anyString())).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> registrationObservationService.saveRegistrationObservations(observationRequests));

        assertEquals("Street not found", exception.getMessage());
        verify(registrationObservationRepository, never()).saveAll(anyList());
    }

    private ParkingRegistrationResponse createParkingRegistrationResponse(RegistrationObservationRequest observationRequest) {
        return new ParkingRegistrationResponse(
                observationRequest.getLicensePlateNumber(),
                observationRequest.getStreetName(),
                LocalDateTime.now(),
                null,
                1L
        );
    }
}
