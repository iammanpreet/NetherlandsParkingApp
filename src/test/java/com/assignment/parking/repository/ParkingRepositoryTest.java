package com.assignment.parking.repository;

import com.assignment.parking.model.ParkingRecord;
import com.assignment.parking.model.Street;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class ParkingRepositoryTest {

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private EntityManager entityManager;
    @Test
    public void findByLicensePlateNumberAndEndTimeIsNull_ShouldReturnParkingRecord() {
        Street street = new Street();
        street.setName("Java");
        entityManager.persist(street);
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setLicensePlateNumber("License1");
        parkingRecord.setStreet(street);
        parkingRecord.setStartTime(LocalDateTime.now());

        parkingRepository.save(parkingRecord);
        ParkingRecord foundRecord = parkingRepository.findByLicensePlateNumberAndEndTimeIsNull("License1");

        assertNotNull(foundRecord);
        assertEquals("License1", foundRecord.getLicensePlateNumber());
        assertEquals("Java", foundRecord.getStreet().getName());
        assertEquals(LocalDateTime.now().getDayOfYear(), foundRecord.getStartTime().getDayOfYear());
        entityManager.clear();
    }

    @Test
    public void findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual_ShouldReturnParkingRecord() {
        Street street = new Street();
        street.setName("Java");
        entityManager.persist(street);
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setLicensePlateNumber("License2");
        parkingRecord.setStreet(street);
        parkingRecord.setStartTime(LocalDateTime.now().minusDays(1));
        parkingRecord.setEndTime(LocalDateTime.now().plusDays(1));

        parkingRepository.save(parkingRecord);
            ParkingRecord foundRecord = parkingRepository.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualIgnoreCase(
                "License2", "Java", LocalDateTime.now());

        assertNotNull(foundRecord);
        assertEquals("License2", foundRecord.getLicensePlateNumber());
        assertEquals("Java", foundRecord.getStreet().getName());
        assertEquals(LocalDateTime.now().minusDays(1).getDayOfYear(), foundRecord.getStartTime().getDayOfYear());
        assertEquals(LocalDateTime.now().plusDays(1).getDayOfYear(), foundRecord.getEndTime().getDayOfYear());
        entityManager.clear();
    }
}

