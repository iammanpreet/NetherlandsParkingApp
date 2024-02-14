package com.assignment.parking.model.response;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class UnregisteredLicencePlateResponse {

    @NotBlank
    private String licensePlateNumber;
    @NotBlank
    private String streetName;
    @NotBlank
    private LocalDateTime observationDate;
    @NotBlank
    private Long unregisteredId;

    public UnregisteredLicencePlateResponse(String licensePlateNumber, String streetName, LocalDateTime observationDate, Long unregisteredId) {
        this.licensePlateNumber = licensePlateNumber;
        this.streetName = streetName;
        this.observationDate = observationDate;
        this.unregisteredId = unregisteredId;
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
    public Long getUnregisteredId() {
        return unregisteredId;
    }
}
