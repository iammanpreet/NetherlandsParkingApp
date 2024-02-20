package com.assignment.parking.service.impl;

import com.assignment.parking.constants.Constants;
import com.assignment.parking.exception.NotFoundException;
import com.assignment.parking.exception.VehicleAlreadyParkedException;
import com.assignment.parking.model.Street;
import com.assignment.parking.model.request.PriceCalculationRequest;
import com.assignment.parking.model.request.UnregisterParkingRequest;
import com.assignment.parking.model.ParkingRecord;
import com.assignment.parking.model.request.ParkingRegistrationRequest;
import com.assignment.parking.model.request.RegisterParkingRequest;
import com.assignment.parking.model.response.ParkingRegistrationResponse;
import com.assignment.parking.model.response.UnregisterParkingResponse;
import com.assignment.parking.repository.ParkingRepository;
import com.assignment.parking.service.ParkingService;

import com.assignment.parking.service.PriceCalculationService;
import com.assignment.parking.service.StreetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ParkingServiceImpl implements ParkingService {
    private final ParkingRepository parkingRepository;
    private final StreetService streetService;

    private final PriceCalculationService priceCalculationService;
    private static final Logger logger = LoggerFactory.getLogger(ParkingServiceImpl.class);

    @Autowired
    public ParkingServiceImpl(ParkingRepository parkingRepository, StreetServiceImpl streetService, PriceCalculationService priceCalculationService) {
        this.parkingRepository = parkingRepository;
        this.streetService = streetService;
        this.priceCalculationService = priceCalculationService;
    }

    @Override
    @Transactional
    public void registerParkingSession(RegisterParkingRequest registerParkingRequest) {
        Optional<Street> street = streetService.getStreetByName(registerParkingRequest.getStreetName());
        if(street.isEmpty()){
            logger.error("Street Not found with street name: {} ", registerParkingRequest.getStreetName());
            throw new NotFoundException("Street not found");
        }
        ParkingRecord existingParkingRecord = findByLicensePlateNumberAndEndTimeIsNull(registerParkingRequest.getLicensePlateNumber());
        if (null != existingParkingRecord) {
            logger.error("Vehicle already parked for license plate number: {} ", registerParkingRequest.getLicensePlateNumber());
            throw new VehicleAlreadyParkedException(Constants.VEHICLE_ALREADY_PARKED_EXCEPTION);
        }

        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setLicensePlateNumber(registerParkingRequest.getLicensePlateNumber());
        parkingRecord.setStreet(street.get());
        parkingRecord.setStartTime(LocalDateTime.now());
        parkingRepository.save(parkingRecord);
    }

    @Override
    @Transactional
    public UnregisterParkingResponse unregisterParkingSession(UnregisterParkingRequest unregisterParkingRequest) {
        ParkingRecord parkingRecord = parkingRepository.findByLicensePlateNumberAndEndTimeIsNull(unregisterParkingRequest.getLicensePlateNumber());
        if (parkingRecord == null) {
            logger.error("Vehicle not found in parking entry for license plate number: {} ", unregisterParkingRequest.getLicensePlateNumber());
            throw new NotFoundException(Constants.NO_VEHICLE_PARKED_EXCEPTION + unregisterParkingRequest.getLicensePlateNumber());
        }
        parkingRecord.setEndTime(LocalDateTime.now());
        parkingRepository.save(parkingRecord);
        BigDecimal parkingCost = priceCalculationService.calculateParkingCost(convertToPriceCalculationRequest(parkingRecord));
        return convertToUnregisterParkingResponse(parkingRecord,parkingCost);
    }

    @Override
    public Optional<ParkingRegistrationResponse> findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(ParkingRegistrationRequest parkingRegistrationRequest) {
        ParkingRecord parkingRecord = parkingRepository.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualIgnoreCase(parkingRegistrationRequest.getLicensePlateNumber(),parkingRegistrationRequest.getStreetName(),parkingRegistrationRequest.getObservationDate());
        if(null != parkingRecord) {
            ParkingRegistrationResponse parkingRegistrationResponse = new ParkingRegistrationResponse(
                    parkingRecord.getLicensePlateNumber(),
                    parkingRecord.getStreet().getName(),
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

    private PriceCalculationRequest convertToPriceCalculationRequest(ParkingRecord parkingRecord){
        PriceCalculationRequest priceCalculationRequest = new PriceCalculationRequest();
        priceCalculationRequest.setStreetName(parkingRecord.getStreet().getName());
        priceCalculationRequest.setStartTime(parkingRecord.getStartTime());
        priceCalculationRequest.setEndTime(parkingRecord.getEndTime());
        return priceCalculationRequest;
    }

    private UnregisterParkingResponse convertToUnregisterParkingResponse(ParkingRecord parkingRecord, BigDecimal parkingCost){
        UnregisterParkingResponse unregisterParkingResponse = new UnregisterParkingResponse();
        unregisterParkingResponse.setLicensePlateNumber(parkingRecord.getLicensePlateNumber());
        unregisterParkingResponse.setParkingCost(parkingCost);
        unregisterParkingResponse.setMessage("Parking has been ended successfully");
        return unregisterParkingResponse;
    }
}
