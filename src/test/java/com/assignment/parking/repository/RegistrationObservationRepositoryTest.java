package com.assignment.parking.repository;

import com.assignment.parking.model.RegistrationObservationRecord;
import com.assignment.parking.model.Street;
import jakarta.persistence.EntityManager;
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
    @Autowired
    private EntityManager entityManager;
    @Test
    public void saveRegistrationObservationRecord_ShouldSaveAndRetrieveRecord() {
        Street street = new Street();
        street.setName("Java");
        entityManager.persist(street);

        // Create a new observation record with the saved street
        RegistrationObservationRecord observationRecord = new RegistrationObservationRecord();
        LocalDateTime observationDate = LocalDateTime.now().minusDays(2);
        observationRecord.setObservationDate(observationDate);
        observationRecord.setLicensePlateNumber("License1");
        observationRecord.setStreet(street);

        // Save the observation record
        registrationObservationRepository.save(observationRecord);

        // Retrieve all records
        List<RegistrationObservationRecord> savedRecords = registrationObservationRepository.findAll();

        // Assert
        assertNotNull(savedRecords);
        assertEquals(1, savedRecords.size());

        RegistrationObservationRecord savedRecord = savedRecords.get(0);
        assertEquals(observationDate, savedRecord.getObservationDate());
        assertEquals("License1", savedRecord.getLicensePlateNumber());
        assertEquals("Java", savedRecord.getStreet().getName());
        entityManager.clear();
    }
}
