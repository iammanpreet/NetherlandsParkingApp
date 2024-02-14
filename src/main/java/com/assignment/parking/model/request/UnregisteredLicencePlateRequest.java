package com.assignment.parking.model.request;

import jakarta.validation.constraints.NotBlank;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;

public class UnregisteredLicencePlateRequest {
    @NotBlank
    private String licensePlateNumber;
    @NotBlank
    private String streetName;
    @NotNull
    private LocalDateTime observationDate;

    public UnregisteredLicencePlateRequest(String licensePlateNumber, String streetName, LocalDateTime observationDate) {
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
