package com.assignment.parking.service.impl;
import com.assignment.parking.model.request.PriceCalculationRequest;
import com.assignment.parking.service.PriceCalculationService;
import com.assignment.parking.service.StreetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DayOfWeek;

@Service
public class PriceCalculationServiceImpl implements PriceCalculationService {

    private final StreetService streetService;
    private static final Logger logger = LoggerFactory.getLogger(PriceCalculationServiceImpl.class);

    @Autowired
    public PriceCalculationServiceImpl(StreetService streetService) {
        this.streetService = streetService;
    }

    public BigDecimal calculateParkingCost(PriceCalculationRequest priceCalculationRequest) {
        Duration totalDuration = calculateParkingDuration(priceCalculationRequest.getStartTime(),priceCalculationRequest.getEndTime());
        logger.info("Duration of parking: {} ", totalDuration);

        Duration freeParkingDurationhours  = calculateFreeParkingHours(priceCalculationRequest.getStartTime(),priceCalculationRequest.getEndTime());
        //chargeable duration
        Duration chargeableDuration = totalDuration.minus(freeParkingDurationhours);
        logger.info("Chargeable Duration of parking: {} ", chargeableDuration);
        if(chargeableDuration.isNegative()) {
            chargeableDuration = Duration.ZERO;
        }
        BigDecimal pricePerMinute = streetService.getPriceByStreetName(priceCalculationRequest.getStreetName());

        BigDecimal cost = pricePerMinute.multiply(new BigDecimal(chargeableDuration.toMinutes()));

        BigDecimal costInEuros = cost.divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        logger.info("Cost for the parking: {} ", costInEuros);
        return costInEuros.setScale(2, RoundingMode.HALF_UP);
    }
    private static Duration calculateFreeParkingHours(LocalDateTime startTime, LocalDateTime endTime) {
        // Compute number of free hours for each day
        long freeHours = 0;
        long freeMinutes = 0;
        LocalDateTime currentDateTime = null;
        for (currentDateTime = startTime; currentDateTime.isBefore(endTime.plusHours(1)); currentDateTime = currentDateTime.plusHours(1)) {
            DayOfWeek dayOfWeek = currentDateTime.getDayOfWeek();
            LocalTime currentTime = currentDateTime.toLocalTime();

            // Check if it's Sunday or falls within the nightly free parking hours
            if (dayOfWeek == DayOfWeek.SUNDAY || (currentTime.isAfter(LocalTime.of(21, 0)) || currentTime.compareTo(LocalTime.of(8, 0)) <=0)) {
                if(Duration.between(currentDateTime.minusHours(1),endTime).toMinutes() >= 60) {
                    freeHours++;
                } else{
                    freeMinutes = Duration.between(endTime,currentDateTime).toMinutes();
                }
            }

        }


        return  Duration.ofHours(freeHours).plusMinutes(freeMinutes);
    }

    private Duration calculateParkingDuration(LocalDateTime startTime, LocalDateTime endTime) {
        return Duration.between(startTime, endTime);
    }
}

