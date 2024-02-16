package com.assignment.parking.service;

import com.assignment.parking.model.request.PriceCalculationRequest;

import java.math.BigDecimal;

public interface PriceCalculationService {
    BigDecimal calculateParkingCost(PriceCalculationRequest priceCalculationRequest);
}
