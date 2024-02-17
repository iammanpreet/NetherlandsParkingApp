package com.assignment.parking.model.response;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class PriceCalculationResponse {
    @NotBlank
    private String streetName;
    @NotBlank
    private BigDecimal price;

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
