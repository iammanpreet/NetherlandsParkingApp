package com.assignment.parking.service;

import com.assignment.parking.model.Street;

import java.math.BigDecimal;
import java.util.Optional;

public interface StreetService {
    Optional<Street> getStreetByName(String streetName);
    BigDecimal getPriceByStreetName(String streetName);
}
