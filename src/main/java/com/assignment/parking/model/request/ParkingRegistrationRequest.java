package com.assignment.parking.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
/**
 * It is a practice to keep the model and Entity layer separate to have loose coupling between model for service and entity
 * */
public class ParkingRegistrationRequest {
    @NotBlank
    private String licensePlateNumber;
    @NotBlank
    private String streetName;
    @NotNull
    private LocalDateTime observationDate;

    public ParkingRegistrationRequest(String licensePlateNumber, String streetName, LocalDateTime observationDate) {
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
