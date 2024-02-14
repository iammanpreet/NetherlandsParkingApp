package com.assignment.parking.service;

import com.assignment.parking.BaseTest;
import com.assignment.parking.constants.Constants;
import com.assignment.parking.exception.NotFoundException;
import com.assignment.parking.exception.VehicleAlreadyParkedException;
import com.assignment.parking.model.request.UnregisterParkingRequest;
import com.assignment.parking.service.impl.ParkingServiceImpl;
import com.assignment.parking.model.ParkingRecord;
import com.assignment.parking.model.request.ParkingRegistrationRequest;
import com.assignment.parking.model.request.RegisterParkingRequest;
import com.assignment.parking.model.response.ParkingRegistrationResponse;
import com.assignment.parking.repository.ParkingRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class ParkingServiceImplTest extends BaseTest {

    @Mock
    private ParkingRepository parkingRepository;

    @InjectMocks
    private ParkingServiceImpl parkingService;

    @Test
    void testRegisterParkingSession_Success() {
        RegisterParkingRequest request = new RegisterParkingRequest("License1", "Street1");
        when(parkingRepository.findByLicensePlateNumberAndEndTimeIsNull(anyString())).thenReturn(null);

        assertDoesNotThrow(() -> parkingService.registerParkingSession(request));

        verify(parkingRepository, times(1)).save(any(ParkingRecord.class));
    }

    @Test
    void testRegisterParkingSession_VehicleAlreadyParked() {
        RegisterParkingRequest request = new RegisterParkingRequest("License1", "Street1");
        when(parkingRepository.findByLicensePlateNumberAndEndTimeIsNull(anyString())).thenReturn(new ParkingRecord());

        VehicleAlreadyParkedException exception = assertThrows(VehicleAlreadyParkedException.class,
                () -> parkingService.registerParkingSession(request));

        Assertions.assertEquals(Constants.VEHICLE_ALREADY_PARKED_EXCEPTION, exception.getMessage());
        verify(parkingRepository, never()).save(any(ParkingRecord.class));
    }

    @Test
    void testUnregisterParkingSession_Success() {
        UnregisterParkingRequest request = new UnregisterParkingRequest("ABC123");
        ParkingRecord parkingRecord = new ParkingRecord();
        when(parkingRepository.findByLicensePlateNumberAndEndTimeIsNull(anyString())).thenReturn(parkingRecord);

        assertDoesNotThrow(() -> parkingService.unregisterParkingSession(request));

        assertNotNull(parkingRecord.getEndTime());
        verify(parkingRepository, times(1)).save(parkingRecord);
    }

    @Test
    void testUnregisterParkingSession_VehicleNotFound() {
        UnregisterParkingRequest request = new UnregisterParkingRequest("License1");
        when(parkingRepository.findByLicensePlateNumberAndEndTimeIsNull(anyString())).thenReturn(null);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> parkingService.unregisterParkingSession(request));

        assertEquals(Constants.NO_VEHICLE_PARKED_EXCEPTION + "License1", exception.getMessage());
        verify(parkingRepository, never()).save(any(ParkingRecord.class));
    }

    @Test
    void testFindByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual() {
        ParkingRegistrationRequest request = new ParkingRegistrationRequest("License1", "Street1", LocalDateTime.now());
        ParkingRecord parkingRecord = new ParkingRecord();
        when(parkingRepository.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(anyString(), anyString(), any(LocalDateTime.class)))
                .thenReturn(parkingRecord);

        Optional<ParkingRegistrationResponse> response = parkingService.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(request);

        assertTrue(response.isPresent());
        assertEquals(parkingRecord.getLicensePlateNumber(), response.get().getLicensePlateNumber());
        assertEquals(parkingRecord.getStreetName(), response.get().getStreetName());
        assertEquals(parkingRecord.getStartTime(), response.get().getStartTime());
        assertEquals(parkingRecord.getEndTime(), response.get().getEndTime());
        assertEquals(parkingRecord.getParkingId(), response.get().getParkingId());
    }

    @Test
    void testFindByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual_NotFound() {
        ParkingRegistrationRequest request = new ParkingRegistrationRequest("License1", "Street1", LocalDateTime.now());
        when(parkingRepository.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(anyString(), anyString(), any(LocalDateTime.class)))
                .thenReturn(null);

        Optional<ParkingRegistrationResponse> response = parkingService.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(request);

        assertTrue(response.isEmpty());
    }
}
