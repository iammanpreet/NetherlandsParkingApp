package com.assignment.parking.model.response;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class ParkingRegistrationResponse {
    @NotBlank
    private String licensePlateNumber;
    @NotBlank
    private String streetName;
    @NotBlank
    private LocalDateTime startTime;
    @NotBlank
    private LocalDateTime endTime;
    private Long parkingId;

    public ParkingRegistrationResponse(String licensePlateNumber, String streetName, LocalDateTime startTime, LocalDateTime endTime, Long parkingId) {
        this.licensePlateNumber = licensePlateNumber;
        this.streetName = streetName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.parkingId = parkingId;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public String getStreetName() {
        return streetName;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public Long getParkingId() {
        return parkingId;
    }
}
