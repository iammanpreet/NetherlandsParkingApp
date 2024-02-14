package com.assignment.parking.service.impl;

import com.assignment.parking.constants.Constants;
import com.assignment.parking.exception.NotFoundException;
import com.assignment.parking.exception.VehicleAlreadyParkedException;
import com.assignment.parking.model.request.UnregisterParkingRequest;
import com.assignment.parking.model.ParkingRecord;
import com.assignment.parking.model.request.ParkingRegistrationRequest;
import com.assignment.parking.model.request.RegisterParkingRequest;
import com.assignment.parking.model.response.ParkingRegistrationResponse;
import com.assignment.parking.repository.ParkingRepository;
import com.assignment.parking.service.ParkingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingServiceImpl implements ParkingService {
    private final ParkingRepository parkingRepository;
    private static final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    @Autowired
    public ParkingServiceImpl(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    @Override
    @Transactional
    public void registerParkingSession(RegisterParkingRequest registerParkingRequest) {
        ParkingRecord existingParkingRecord = findByLicensePlateNumberAndEndTimeIsNull(registerParkingRequest.getLicensePlateNumber());
        if (null != existingParkingRecord) {
            logger.error("Vehicle already parked for license plate number: {} ", registerParkingRequest.getLicensePlateNumber());
            throw new VehicleAlreadyParkedException(Constants.VEHICLE_ALREADY_PARKED_EXCEPTION);
        }
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setLicensePlateNumber(registerParkingRequest.getLicensePlateNumber());
        parkingRecord.setStreetName(registerParkingRequest.getStreetName());
        parkingRecord.setStartTime(LocalDateTime.now());
        parkingRepository.save(parkingRecord);
    }

    @Override
    @Transactional
    public void unregisterParkingSession(UnregisterParkingRequest unregisterParkingRequest) {
        ParkingRecord parkingRecord = parkingRepository.findByLicensePlateNumberAndEndTimeIsNull(unregisterParkingRequest.getLicensePlateNumber());
        if (parkingRecord == null) {
            logger.error("Vehicle not found in parking entry for license plate number: {} ", unregisterParkingRequest.getLicensePlateNumber());
            throw new NotFoundException(Constants.NO_VEHICLE_PARKED_EXCEPTION + unregisterParkingRequest.getLicensePlateNumber());
        }
        parkingRecord.setEndTime(LocalDateTime.now());
        parkingRepository.save(parkingRecord);
    }

    @Override
    public Optional<ParkingRegistrationResponse> findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(ParkingRegistrationRequest parkingRegistrationRequest) {
        ParkingRecord parkingRecord = parkingRepository.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(parkingRegistrationRequest.getLicensePlateNumber(),parkingRegistrationRequest.getStreetName(),parkingRegistrationRequest.getObservationDate());
        if(null != parkingRecord) {
            ParkingRegistrationResponse parkingRegistrationResponse = new ParkingRegistrationResponse(
                    parkingRecord.getLicensePlateNumber(),
                    parkingRecord.getStreetName(),
                    parkingRecord.getStartTime(),
                    parkingRecord.getEndTime(),
                    parkingRecord.getParkingId()
            );
            return Optional.of(parkingRegistrationResponse);
        }
        logger.info("Parking entry not found for license plate: {}", parkingRegistrationRequest.getLicensePlateNumber());
        return Optional.empty();
    }

    private ParkingRecord findByLicensePlateNumberAndEndTimeIsNull(String licensePlate) {
        logger.info("fetch the active parking record for license Plate: {}" , licensePlate);
        return parkingRepository.findByLicensePlateNumberAndEndTimeIsNull(licensePlate);
    }
}
