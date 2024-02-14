package com.assignment.parking.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class RegistrationObservationRequest {
    @NotBlank
    private String licensePlateNumber;
    @NotBlank
    private String streetName;
    @NotNull
    private LocalDateTime observationDate;

    public RegistrationObservationRequest(String licensePlateNumber, String streetName, LocalDateTime observationDate) {
        this.licensePlateNumber = licensePlateNumber;
        this.streetName = streetName;
        this.observationDate = observationDate;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public LocalDateTime getObservationDate() {
        return observationDate;
    }
}
