package com.assignment.parking.model.response;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class UnregisterParkingResponse {
    @NotBlank
    private String licensePlateNumber;
    @NotBlank
    private BigDecimal parkingCost;

    private String costCurrency = "EUR";

    private String message;

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public BigDecimal getParkingCost() {
        return parkingCost;
    }

    public void setParkingCost(BigDecimal parkingCost) {
        this.parkingCost = parkingCost;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCostCurrency() {
        return costCurrency;
    }

    public void setCostCurrency(String costCurrency) {
        this.costCurrency = costCurrency;
    }
}
