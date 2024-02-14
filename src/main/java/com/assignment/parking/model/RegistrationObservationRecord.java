package com.assignment.parking.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "RegistrationObservation")
public class RegistrationObservationRecord {
    /**
     * Observation id, primary key in RegistrationObservation
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "observation_id")
    private Long observationId;
    /**
     * Licence plate number
     */
    @Column(name = "license_plate_number", nullable = false)
    private String licensePlateNumber;
    /**
     * Street Name (should be case sensitive, or else we should use enum with all street data)
     */
    @Column(name = "street_name", nullable = false)
    private String streetName;
    /**
     * Observation Date time of the parking of vehicle
     */
    @Column(name = "observation_date", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime observationDate;
    /**
     * If the observation parking is not registered, then insert as unregistered license plate.
     * */
    @ManyToOne
    @JoinColumn(name = "unregistered_id")
    private UnregisteredLicensePlate unregisteredLicensePlate;

    public Long getObservationId() {
        return observationId;
    }

    public void setObservationId(Long observationId) {
        this.observationId = observationId;
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

    public LocalDateTime getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(LocalDateTime observationDate) {
        this.observationDate = observationDate;
    }

    public UnregisteredLicensePlate getUnregisteredLicensePlate() {
        return unregisteredLicensePlate;
    }

    public void setUnregisteredLicensePlate(UnregisteredLicensePlate unregisteredLicensePlate) {
        this.unregisteredLicensePlate = unregisteredLicensePlate;
    }

    /**
     * Note: Assuming a particular parking can be reported multiple time by administrator
     * to have the same entry in reports again.
     * */
}
