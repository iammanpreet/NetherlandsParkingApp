package com.assignment.parking.service;

import com.assignment.parking.model.request.UnregisterParkingRequest;
import com.assignment.parking.model.request.ParkingRegistrationRequest;
import com.assignment.parking.model.request.RegisterParkingRequest;
import com.assignment.parking.model.response.ParkingRegistrationResponse;

import java.util.Optional;

public interface ParkingService {
    void registerParkingSession(RegisterParkingRequest registerParkingRequest);
    void unregisterParkingSession(UnregisterParkingRequest licensePlate);

    Optional<ParkingRegistrationResponse> findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(ParkingRegistrationRequest parkingRegistrationRequest);
}
