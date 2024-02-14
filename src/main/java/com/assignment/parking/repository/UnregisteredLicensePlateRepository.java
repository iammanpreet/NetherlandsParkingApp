package com.assignment.parking.repository;

import com.assignment.parking.model.UnregisteredLicensePlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UnregisteredLicensePlateRepository extends JpaRepository<UnregisteredLicensePlate, Long> {
    List<UnregisteredLicensePlate> findByIsReported(String isReported);
    @Modifying
    @Transactional
    @Query("UPDATE UnregisteredLicensePlate u SET u.isReported = 'Y' WHERE u.unregisteredId IN :ids")
    void updateIsReportedByIds(@Param("ids") List<Long> ids);
}
