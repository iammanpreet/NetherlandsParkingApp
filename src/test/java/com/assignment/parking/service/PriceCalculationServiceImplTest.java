package com.assignment.parking.service;

import com.assignment.parking.model.request.PriceCalculationRequest;
import com.assignment.parking.service.impl.PriceCalculationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class PriceCalculationServiceImplTest {

    @Mock
    private StreetService streetService;

    @InjectMocks
    private PriceCalculationServiceImpl priceCalculationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calculateParkingCost() {
        String streetName = "JavaStreet";
        LocalDateTime startTime = LocalDateTime.now().minusHours(2).minusMinutes(30);
        LocalDateTime endTime = LocalDateTime.now();
        BigDecimal pricePerMinute = new BigDecimal("0.50");
        Duration parkingDuration = Duration.ofMinutes(150);

        PriceCalculationRequest priceCalculationRequest = new PriceCalculationRequest();
        priceCalculationRequest.setEndTime(endTime);
        priceCalculationRequest.setStartTime(startTime);
        priceCalculationRequest.setStreetName(streetName);
        when(streetService.getPriceByStreetName(streetName)).thenReturn(pricePerMinute);
        BigDecimal result = priceCalculationService.calculateParkingCost(priceCalculationRequest);
        assertEquals(new BigDecimal("0.75"), result); // 150 minutes * $0.50/minute
        verify(streetService, times(1)).getPriceByStreetName(streetName);
    }

    @Test
    void calculateParkingCost_ZeroDuration() {
        String streetName = "Java";
        LocalDateTime startTime = LocalDateTime.of(2022, 1, 1, 12, 0);
        LocalDateTime endTime = LocalDateTime.of(2022, 1, 1, 12, 0);
        BigDecimal pricePerMinute = new BigDecimal("0.50");
        Duration parkingDuration = Duration.ofMinutes(0);

        PriceCalculationRequest priceCalculationRequest = new PriceCalculationRequest();
        priceCalculationRequest.setEndTime(endTime);
        priceCalculationRequest.setStartTime(startTime);
        priceCalculationRequest.setStreetName(streetName);

        when(streetService.getPriceByStreetName(streetName)).thenReturn(pricePerMinute);
        BigDecimal result = priceCalculationService.calculateParkingCost(priceCalculationRequest);
        assertEquals(BigDecimal.ZERO.doubleValue(), result.doubleValue()); // Zero minutes * $0.50/minute
        verify(streetService, times(1)).getPriceByStreetName(streetName);
    }
}

