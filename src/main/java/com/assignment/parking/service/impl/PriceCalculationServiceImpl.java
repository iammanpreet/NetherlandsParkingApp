package com.assignment.parking.service.impl;
import com.assignment.parking.model.ParkingRecord;
import com.assignment.parking.model.request.PriceCalculationRequest;
import com.assignment.parking.service.PriceCalculationService;
import com.assignment.parking.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;

@Service
public class PriceCalculationServiceImpl implements PriceCalculationService {

    private final StreetService streetService;

    @Autowired
    public PriceCalculationServiceImpl(StreetService streetService) {
        this.streetService = streetService;
    }

    public BigDecimal calculateParkingCost(PriceCalculationRequest priceCalculationRequest) {
        Duration duration = calculateParkingDuration(priceCalculationRequest);

        BigDecimal pricePerMinute = streetService.getPriceByStreetName(priceCalculationRequest.getStreetName());

        BigDecimal cost = pricePerMinute.multiply(new BigDecimal(duration.toMinutes()));

        return cost.setScale(2, RoundingMode.HALF_UP);
    }

    private Duration calculateParkingDuration(PriceCalculationRequest priceCalculationRequest) {
        return Duration.between(priceCalculationRequest.getStartTime(), priceCalculationRequest.getEndTime());
    }
}

