package com.assignment.parking.repository;

import com.assignment.parking.model.ParkingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ParkingRepository extends JpaRepository<ParkingRecord, Long> {
    ParkingRecord findByLicensePlateNumberAndEndTimeIsNull(String licensePlate);

    @Query("SELECT pr FROM ParkingRecord pr " +
            "WHERE pr.licensePlateNumber = :licensePlateNumber " +
            "AND pr.street.name = :streetName " +
            "AND pr.startTime <= :observationDate " +
            "AND (pr.endTime >= :observationDate or pr.endTime is null)")
    ParkingRecord findByLicensePlateNumberAndStreetNameAndStartTimeLessThanEqualAndEndTimeGreaterThanEqualIgnoreCase(@Param("licensePlateNumber") String licensePlateNumber, @Param("streetName") String streetName, @Param("observationDate") LocalDateTime observationDate);
}
