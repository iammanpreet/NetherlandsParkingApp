package com.assignment.parking.model.request;

import jakarta.validation.constraints.NotBlank;

public class UnregisterParkingRequest {

    @NotBlank
    private String licensePlateNumber;

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }
}
