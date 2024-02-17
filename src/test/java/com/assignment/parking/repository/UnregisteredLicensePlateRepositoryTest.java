package com.assignment.parking.repository;

import com.assignment.parking.model.Street;
import com.assignment.parking.model.UnregisteredLicensePlate;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class UnregisteredLicensePlateRepositoryTest {

    @Autowired
    private UnregisteredLicensePlateRepository unregisteredLicensePlateRepository;
    @Autowired
    private EntityManager entityManager;
    @Test
    public void findByIsReported_ShouldReturnUnreportedPlates() {
        LocalDateTime now = LocalDateTime.now();
        UnregisteredLicensePlate unreportedLicensePlate1 = createUnregisteredLicensePlate("License1",  now.minusDays(1), "N");
        UnregisteredLicensePlate unreportedLicensePlate2 = createUnregisteredLicensePlate("License2", now.minusDays(2), "N");
        UnregisteredLicensePlate reportedLicensePlate = createUnregisteredLicensePlate("License3",  now.minusDays(3), "Y");

        unregisteredLicensePlateRepository.saveAll(Arrays.asList(unreportedLicensePlate1, unreportedLicensePlate2, reportedLicensePlate));
        List<UnregisteredLicensePlate> unreportedLicensePlates = unregisteredLicensePlateRepository.findByIsReported("N");
        assertEquals(2, unreportedLicensePlates.size());
        assertTrue(unreportedLicensePlates.stream().allMatch(plate -> "N".equals(plate.getIsReported())));
    }

    @Test
    public void updateIsReportedByIds_ShouldUpdateIsReported() {
        LocalDateTime now = LocalDateTime.now();
        UnregisteredLicensePlate unreportedLicensePlate1 = createUnregisteredLicensePlate("License1",  now.minusDays(1), "N");
        UnregisteredLicensePlate unreportedLicensePlate2 = createUnregisteredLicensePlate("License2", now.minusDays(2), "N");
        UnregisteredLicensePlate unreportedLicensePlate3 = createUnregisteredLicensePlate("License3",  now.minusDays(3), "N");

        List<UnregisteredLicensePlate> savedPlates = unregisteredLicensePlateRepository.saveAll(Arrays.asList(unreportedLicensePlate1, unreportedLicensePlate2, unreportedLicensePlate3));

        List<Long> plateIdsToUpdate = Arrays.asList(savedPlates.get(0).getUnregisteredId(), savedPlates.get(1).getUnregisteredId());
        unregisteredLicensePlateRepository.updateIsReportedByIds(plateIdsToUpdate);
        List<UnregisteredLicensePlate> updatedLicensePlates = unregisteredLicensePlateRepository.findAllById(plateIdsToUpdate);
        assertTrue(updatedLicensePlates.stream().allMatch(plate -> "N".equals(plate.getIsReported())));
    }

    private UnregisteredLicensePlate createUnregisteredLicensePlate(String licensePlate, LocalDateTime observationDate, String isReported) {
        Street street = new Street();
        street.setPricePerMinute(10);
        street.setName("Java");
        entityManager.persist(street);
        UnregisteredLicensePlate plate = new UnregisteredLicensePlate();
        plate.setLicensePlateNumber(licensePlate);
        plate.setStreet(street);
        plate.setObservationDate(observationDate);
        plate.setIsReported(isReported);
        return plate;
    }
}

