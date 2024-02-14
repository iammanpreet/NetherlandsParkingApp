package com.assignment.parking.repository;

import com.assignment.parking.model.RegistrationObservationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationObservationRepository extends JpaRepository<RegistrationObservationRecord, Long> {
}
