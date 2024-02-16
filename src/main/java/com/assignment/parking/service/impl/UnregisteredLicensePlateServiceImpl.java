package com.assignment.parking.service.impl;

import com.assignment.parking.exception.NotFoundException;
import com.assignment.parking.model.Street;
import com.assignment.parking.model.UnregisteredLicensePlate;
import com.assignment.parking.model.request.UnregisteredLicencePlateRequest;
import com.assignment.parking.model.response.UnregisteredLicencePlateResponse;
import com.assignment.parking.repository.UnregisteredLicensePlateRepository;
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
public class UnregisteredLicensePlateServiceImpl implements UnregisteredLicensePlateService {
    private final UnregisteredLicensePlateRepository unregisteredLicensePlateRepository;
    private final StreetService streetService;
    private static final Logger logger = LoggerFactory.getLogger(UnregisteredLicensePlateServiceImpl.class);

    @Autowired
    public UnregisteredLicensePlateServiceImpl(UnregisteredLicensePlateRepository unregisteredLicensePlateRepository, StreetService streetService) {
        this.unregisteredLicensePlateRepository = unregisteredLicensePlateRepository;
        this.streetService = streetService;
    }

    public UnregisteredLicencePlateResponse saveUnregisteredLicensePlate(UnregisteredLicencePlateRequest unregisteredLicencePlateRequest) {
        Optional<Street> street = streetService.getStreetByName(unregisteredLicencePlateRequest.getStreetName());
        if(street.isEmpty()){
            logger.error("Street Not found with street name: {} ", unregisteredLicencePlateRequest.getStreetName());
            throw new NotFoundException("Street not found");
        }
        UnregisteredLicensePlate unregisteredLicensePlate = new UnregisteredLicensePlate();
        unregisteredLicensePlate.setLicensePlateNumber(unregisteredLicencePlateRequest.getLicensePlateNumber());
        unregisteredLicensePlate.setStreet(street.get());
        unregisteredLicensePlate.setObservationDate(unregisteredLicencePlateRequest.getObservationDate());

        UnregisteredLicensePlate savedUnregisteredLicenseTemplate = unregisteredLicensePlateRepository.save(unregisteredLicensePlate);
        return new UnregisteredLicencePlateResponse(
                savedUnregisteredLicenseTemplate.getLicensePlateNumber(),
                savedUnregisteredLicenseTemplate.getStreet().getName(),
                savedUnregisteredLicenseTemplate.getObservationDate(),
                savedUnregisteredLicenseTemplate.getUnregisteredId()
        );
    }

    @Override
    public Optional<List<UnregisteredLicencePlateResponse>> findByIsReported(String isReported) {
        List<UnregisteredLicencePlateResponse> response = convertToUnregisteredLicensePlateResponse(unregisteredLicensePlateRepository.findByIsReported(isReported));
        if(response.isEmpty()){
            logger.info("Found no pending records needs to be reported for unregister parking");
            return Optional.empty();
        }
        logger.info("Unregistered records size: {}", response.size());
        return Optional.of(response);
    }

    @Override
    public void updateIsReportedByIds(List<Long> ids) {
        unregisteredLicensePlateRepository.updateIsReportedByIds(ids);
    }

    private List<UnregisteredLicencePlateResponse> convertToUnregisteredLicensePlateResponse(List<UnregisteredLicensePlate> unregisteredLicensePlateList) {
        List<UnregisteredLicencePlateResponse> unregisteredLicencePlateResponseList = new ArrayList<>();

            for (UnregisteredLicensePlate unregisteredLicensePlate : unregisteredLicensePlateList) {
                UnregisteredLicencePlateResponse unregisteredLicencePlateResponse = new UnregisteredLicencePlateResponse(
                        unregisteredLicensePlate.getLicensePlateNumber(),
                        unregisteredLicensePlate.getStreet().getName(),
                        unregisteredLicensePlate.getObservationDate(),
                        unregisteredLicensePlate.getUnregisteredId());
                unregisteredLicencePlateResponseList.add(unregisteredLicencePlateResponse);
        }
        return unregisteredLicencePlateResponseList;
    }
}
