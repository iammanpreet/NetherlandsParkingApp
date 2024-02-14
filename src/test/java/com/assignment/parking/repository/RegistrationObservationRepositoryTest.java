package com.assignment.parking.repository;

import com.assignment.parking.model.RegistrationObservationRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class RegistrationObservationRepositoryTest {

    @Autowired
    private RegistrationObservationRepository registrationObservationRepository;

    @Test
    public void saveRegistrationObservationRecord_ShouldSaveAndRetrieveRecord() {
        RegistrationObservationRecord observationRecord = new RegistrationObservationRecord();
        LocalDateTime observationDate = LocalDateTime.now().minusDays(2);
        observationRecord.setObservationDate(observationDate);
        observationRecord.setStreetName("Street1");
        observationRecord.setLicensePlateNumber("License1");

        registrationObservationRepository.save(observationRecord);
        List<RegistrationObservationRecord> savedRecords = registrationObservationRepository.findAll();
        assertNotNull(savedRecords);
        assertEquals(1, savedRecords.size());

        RegistrationObservationRecord savedRecord = savedRecords.get(0);
        assertEquals(observationDate, savedRecord.getObservationDate());
        assertEquals("Street1", savedRecord.getStreetName());
        assertEquals("License1", savedRecord.getLicensePlateNumber());
    }
}

