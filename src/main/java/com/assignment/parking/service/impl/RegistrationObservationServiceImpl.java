package com.assignment.parking.service.impl;

import com.assignment.parking.exception.NotFoundException;
import com.assignment.parking.model.RegistrationObservationRecord;
import com.assignment.parking.model.Street;
import com.assignment.parking.model.request.ParkingRegistrationRequest;
import com.assignment.parking.model.request.RegistrationObservationRequest;
import com.assignment.parking.model.request.UnregisteredLicencePlateRequest;
import com.assignment.parking.model.response.ParkingRegistrationResponse;
import com.assignment.parking.repository.RegistrationObservationRepository;
import com.assignment.parking.service.ParkingService;
import com.assignment.parking.service.RegistrationObservationService;
import com.assignment.parking.service.StreetService;
import com.assignment.parking.service.UnregisteredLicensePlateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationObservationServiceImpl implements RegistrationObservationService {
    private final RegistrationObservationRepository registrationObservationRepository;
    private final ParkingService parkingService;

    private final StreetService streetService;
    private final UnregisteredLicensePlateService unregisteredLicensePlateService;
    private static final Logger logger = LoggerFactory.getLogger(RegistrationObservationServiceImpl.class);

    @Autowired
    public RegistrationObservationServiceImpl(RegistrationObservationRepository registrationObservationRepository, ParkingService parkingService, UnregisteredLicensePlateService unregisteredLicensePlateService, StreetService streetService) {
        this.registrationObservationRepository = registrationObservationRepository;
        this.parkingService = parkingService;
        this.unregisteredLicensePlateService = unregisteredLicensePlateService;
        this.streetService = streetService;
    }

    public void saveRegistrationObservations(List<RegistrationObservationRequest> registrationsObservations) {
        logger.info("Saving Registration observations size: {}", registrationsObservations.size());
        registrationObservationRepository.saveAll(convertToRegistrationObservationRecords(registrationsObservations));
    }

    private List<RegistrationObservationRecord> convertToRegistrationObservationRecords(List<RegistrationObservationRequest> registrationObservationRequests) {
        List<RegistrationObservationRecord> registrationObservationRecords = new ArrayList<>();
        for (RegistrationObservationRequest request : registrationObservationRequests) {
            Optional<Street> street = streetService.getStreetByName(request.getStreetName());
            if(street.isEmpty()){
                logger.error("Street Not found with street name: {} ", request.getStreetName());
                throw new NotFoundException("Street not found");
            }
            RegistrationObservationRecord record = new RegistrationObservationRecord();
            record.setObservationDate(request.getObservationDate());
            record.setStreet(street.get());
            record.setLicensePlateNumber(request.getLicensePlateNumber());
            if(!findByLicensePlateRegisteredForStreetAtObservationTime(request).isPresent()){
                logger.info("Found a suspicious parking without registration for license plate number: {}", record.getLicensePlateNumber());
                unregisteredLicensePlateService.saveUnregisteredLicensePlate(createUnregisteredLicencePlateRequest(request));
            }
            registrationObservationRecords.add(record);
        }
        return registrationObservationRecords;
    }

    private Optional<ParkingRegistrationResponse> findByLicensePlateRegisteredForStreetAtObservationTime(RegistrationObservationRequest request){
        return parkingService.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(convertToParkingRegistrationRequest(request));
    }

    private ParkingRegistrationRequest convertToParkingRegistrationRequest(RegistrationObservationRequest request){
        return new ParkingRegistrationRequest(request.getLicensePlateNumber(),request.getStreetName(),request.getObservationDate());
    }

    private UnregisteredLicencePlateRequest createUnregisteredLicencePlateRequest(RegistrationObservationRequest request){
        return new UnregisteredLicencePlateRequest(request.getLicensePlateNumber(),request.getStreetName(),request.getObservationDate());
    }
}
