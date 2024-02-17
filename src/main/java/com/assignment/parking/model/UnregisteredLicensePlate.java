package com.assignment.parking.model;

import com.assignment.parking.constants.Constants;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity(name = "UnregisteredLicensePlate")
public class UnregisteredLicensePlate {
    /**
     * Primary key of UnregisteredLicensePlate
     * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unregistered_id")
    private Long unregisteredId;
    /**
     * Licence plate number
     */
    @Column(name = "license_plate_number", nullable = false)
    private String licensePlateNumber;
    /**
     * street name
     */
    @ManyToOne
    @JoinColumn(name = "street_id")
    private Street street;
    /**
     * Observation date time
     */
    @Column(name = "observation_date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime observationDate;
    /**
     * is already considered for report or not.
     */
    @Column(name = "is_reported", columnDefinition = "CHAR(1)")
    private String isReported = Constants.DEFAULT_REPORTING_VALUE;

    public Long getUnregisteredId() {
        return unregisteredId;
    }

    public void setUnregisteredId(Long unregisteredId) {
        this.unregisteredId = unregisteredId;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public Street getStreet() {
        return street;
    }

    public void setStreet(Street street) {
        this.street = street;
    }

    public LocalDateTime getObservationDate() {
        return observationDate;
    }

    public void setObservationDate(LocalDateTime observationDate) {
        this.observationDate = observationDate;
    }

    public String getIsReported() {
        return isReported;
    }

    public void setIsReported(String isReported) {
        this.isReported = isReported;
    }
}
