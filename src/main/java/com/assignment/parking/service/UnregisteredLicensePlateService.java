package com.assignment.parking.service;

import com.assignment.parking.model.request.UnregisteredLicencePlateRequest;
import com.assignment.parking.model.response.UnregisteredLicencePlateResponse;

import java.util.List;
import java.util.Optional;

public interface UnregisteredLicensePlateService {
    UnregisteredLicencePlateResponse saveUnregisteredLicensePlate(UnregisteredLicencePlateRequest unregisteredLicencePlateRequest);
    Optional<List<UnregisteredLicencePlateResponse>> findByIsReported(String isReported);
    void updateIsReportedByIds(List<Long> ids);
}
