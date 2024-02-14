package com.assignment.parking.service;

import com.assignment.parking.model.request.RegistrationObservationRequest;

import java.util.List;


public interface RegistrationObservationService {
    void saveRegistrationObservations(List<RegistrationObservationRequest> registrationsObservations);
}
