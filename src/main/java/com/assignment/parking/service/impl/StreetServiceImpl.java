package com.assignment.parking.service.impl;

import com.assignment.parking.exception.NotFoundException;
import com.assignment.parking.model.Street;
import com.assignment.parking.repository.StreetRepository;
import com.assignment.parking.service.StreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class StreetServiceImpl implements StreetService {

    private final StreetRepository streetRepository;
    @Autowired
    public StreetServiceImpl(StreetRepository streetRepository){
        this.streetRepository = streetRepository;
    }
    public Optional<Street> getStreetByName(String streetName) {
        return streetRepository.findByNameIgnoreCase(streetName);
    }

    @Override
    public BigDecimal getPriceByStreetName(String streetName) {
        Street street = streetRepository.findByNameIgnoreCase(streetName)
                .orElseThrow(() -> new NotFoundException("Street price not found for street: " + streetName));

        return new BigDecimal(street.getPricePerMinute());
    }
}
