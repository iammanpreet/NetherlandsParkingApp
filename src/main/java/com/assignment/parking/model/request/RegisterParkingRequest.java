package com.assignment.parking.model.request;

import jakarta.validation.constraints.NotBlank;

public class RegisterParkingRequest {
    @NotBlank
    private String licensePlateNumber;
    @NotBlank
    private String streetName;

    public RegisterParkingRequest(String licensePlateNumber, String streetName) {
        this.licensePlateNumber = licensePlateNumber;
        this.streetName = streetName;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public String getStreetName() {
        return streetName;
    }

 }
