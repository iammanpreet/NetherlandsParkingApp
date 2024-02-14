package com.assignment.parking.schedule;

import com.assignment.parking.BaseTest;
import com.assignment.parking.config.CsvConfig;
import com.assignment.parking.model.response.UnregisteredLicencePlateResponse;
import com.assignment.parking.service.UnregisteredLicensePlateService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UnregisteredVehicleCheckSchedulerTest extends BaseTest {

    @Mock
    private UnregisteredLicensePlateService unregisteredLicensePlateService;

    @Mock
    private CsvConfig csvConfig;
    @InjectMocks
    private UnregisteredVehicleCheckScheduler unregisteredVehicleCheckScheduler;

    @Test
    public void testCheckUnregisteredParkings() {
        when(unregisteredLicensePlateService.findByIsReported("N")).thenReturn(Optional.of(Collections.singletonList(
                new UnregisteredLicencePlateResponse("License1", "Street1",LocalDateTime.now(), 1L)
        )));
        when(csvConfig.getCsvFilePath()).thenReturn("");
        unregisteredVehicleCheckScheduler.checkUnregisteredParkings();
        verify(unregisteredLicensePlateService, times(1)).findByIsReported("N");
        verify(unregisteredLicensePlateService, times(1)).updateIsReportedByIds(Collections.singletonList(1L));
    }
    @Test
    public void testCheckUnregisteredParkings_EmptySet() {
        when(unregisteredLicensePlateService.findByIsReported("N")).thenReturn(Optional.empty());

        unregisteredVehicleCheckScheduler.checkUnregisteredParkings();
        verify(unregisteredLicensePlateService, times(1)).findByIsReported("N");
        verify(unregisteredLicensePlateService, never()).updateIsReportedByIds(Collections.singletonList(1L));
    }
}

