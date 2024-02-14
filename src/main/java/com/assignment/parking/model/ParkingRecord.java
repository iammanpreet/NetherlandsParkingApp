package com.assignment.parking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "ParkingRecord")
public class ParkingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_id")
    private Long parkingId;
    /**
     * Licence plate number
     * Unique with Licence Plate and end date null
     */
    @Column(name = "license_plate_number", nullable = false)
    private String licensePlateNumber;
    /**
     * Street name for the parking spot
     */
    @Column(name = "street_name", nullable = false)
    private String streetName;

    /**
     * Start time of the parking
     */
    @Column(name = "start_time", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")

    private LocalDateTime startTime;
    /**
     * End time of the parking
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;

    public Long getParkingId() {
        return parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
