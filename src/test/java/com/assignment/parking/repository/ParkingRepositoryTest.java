package com.assignment.parking.repository;

import com.assignment.parking.model.ParkingRecord;
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

    @Test
    public void findByLicensePlateNumberAndEndTimeIsNull_ShouldReturnParkingRecord() {
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setLicensePlateNumber("License1");
        parkingRecord.setStreetName("Street1");
        parkingRecord.setStartTime(LocalDateTime.now());

        parkingRepository.save(parkingRecord);
        ParkingRecord foundRecord = parkingRepository.findByLicensePlateNumberAndEndTimeIsNull("License1");

        assertNotNull(foundRecord);
        assertEquals("License1", foundRecord.getLicensePlateNumber());
        assertEquals("Street1", foundRecord.getStreetName());
        assertEquals(LocalDateTime.now().getDayOfYear(), foundRecord.getStartTime().getDayOfYear());
    }

    @Test
    public void findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual_ShouldReturnParkingRecord() {
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setLicensePlateNumber("License2");
        parkingRecord.setStreetName("Street2");
        parkingRecord.setStartTime(LocalDateTime.now().minusDays(1));
        parkingRecord.setEndTime(LocalDateTime.now().plusDays(1));

        parkingRepository.save(parkingRecord);
        ParkingRecord foundRecord = parkingRepository.findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
                "License2", "Street2", LocalDateTime.now());

        assertNotNull(foundRecord);
        assertEquals("License2", foundRecord.getLicensePlateNumber());
        assertEquals("Street2", foundRecord.getStreetName());
        assertEquals(LocalDateTime.now().minusDays(1).getDayOfYear(), foundRecord.getStartTime().getDayOfYear());
        assertEquals(LocalDateTime.now().plusDays(1).getDayOfYear(), foundRecord.getEndTime().getDayOfYear());
    }
}

