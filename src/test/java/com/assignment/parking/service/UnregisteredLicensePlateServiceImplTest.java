package com.assignment.parking.service;

import com.assignment.parking.BaseTest;
import com.assignment.parking.model.Street;
import com.assignment.parking.model.UnregisteredLicensePlate;
import com.assignment.parking.model.request.UnregisteredLicencePlateRequest;
import com.assignment.parking.model.response.UnregisteredLicencePlateResponse;
import com.assignment.parking.repository.UnregisteredLicensePlateRepository;
import com.assignment.parking.service.impl.StreetServiceImpl;
import com.assignment.parking.service.impl.UnregisteredLicensePlateServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class UnregisteredLicensePlateServiceImplTest extends BaseTest {

    @Mock
    private UnregisteredLicensePlateRepository unregisteredLicensePlateRepository;

    @Mock
    private StreetServiceImpl streetService;
    @InjectMocks
    private UnregisteredLicensePlateServiceImpl unregisteredLicensePlateService;

    @Test
    void testSaveUnregisteredLicensePlate_Success() {
        UnregisteredLicencePlateRequest request = createUnregisteredPlateRequest();
        UnregisteredLicensePlate savedUnregisteredPlate = createUnregisteredPlate(request);
        when(unregisteredLicensePlateRepository.save(any())).thenReturn(savedUnregisteredPlate);
        Street street = new Street();
        when(streetService.getStreetByName(anyString())).thenReturn(Optional.of(street));

        UnregisteredLicencePlateResponse response = unregisteredLicensePlateService.saveUnregisteredLicensePlate(request);

        assertNotNull(response);
        assertEquals(request.getLicensePlateNumber(), response.getLicensePlateNumber());
        assertEquals(request.getStreetName(), response.getStreetName());
        assertEquals(request.getObservationDate(), response.getObservationDate());
        assertEquals(savedUnregisteredPlate.getUnregisteredId(), response.getUnregisteredId());

        verify(unregisteredLicensePlateRepository, times(1)).save(any());
    }

    @Test
    void testFindByIsReported_Success() {
        List<UnregisteredLicensePlate> unregisteredLicensePlates = createUnregisteredPlates();
        when(unregisteredLicensePlateRepository.findByIsReported(any())).thenReturn(unregisteredLicensePlates);
        Street street = new Street();
        street.setName("Java");
        when(streetService.getStreetByName(anyString())).thenReturn(Optional.of(street));

        Optional<List<UnregisteredLicencePlateResponse>> responseListOptional = unregisteredLicensePlateService.findByIsReported("N");

        assertTrue(responseListOptional.isPresent());
        List<UnregisteredLicencePlateResponse> responseList = responseListOptional.get();
        assertEquals(unregisteredLicensePlates.size(), responseList.size());

        verify(unregisteredLicensePlateRepository, times(1)).findByIsReported(any());
    }

    @Test
    void testFindByIsReported_Empty() {
        when(unregisteredLicensePlateRepository.findByIsReported(any())).thenReturn(new ArrayList<>());

        Optional<List<UnregisteredLicencePlateResponse>> responseListOptional = unregisteredLicensePlateService.findByIsReported("N");

        assertTrue(responseListOptional.isEmpty());

        verify(unregisteredLicensePlateRepository, times(1)).findByIsReported(any());
    }

    @Test
    void testUpdateIsReportedByIds_Success() {
        List<Long> ids = createUnregisteredPlateIds();
        doNothing().when(unregisteredLicensePlateRepository).updateIsReportedByIds(ids);

        assertDoesNotThrow(() -> unregisteredLicensePlateService.updateIsReportedByIds(ids));

        verify(unregisteredLicensePlateRepository, times(1)).updateIsReportedByIds(anyList());
    }

    private UnregisteredLicencePlateRequest createUnregisteredPlateRequest() {
        return new UnregisteredLicencePlateRequest("License1", "Java", LocalDateTime.now());
    }

    private UnregisteredLicensePlate createUnregisteredPlate(UnregisteredLicencePlateRequest request) {
        UnregisteredLicensePlate unregisteredPlate = new UnregisteredLicensePlate();
        unregisteredPlate.setLicensePlateNumber(request.getLicensePlateNumber());
        unregisteredPlate.setObservationDate(request.getObservationDate());
        unregisteredPlate.setUnregisteredId(1L);
        Street street = new Street();
        street.setName("Java");
        street.setPricePerMinute(15);
        unregisteredPlate.setStreet(street);
        return unregisteredPlate;
    }

    private List<UnregisteredLicensePlate> createUnregisteredPlates() {
        List<UnregisteredLicensePlate> unregisteredLicensePlates = new ArrayList<>();
        unregisteredLicensePlates.add(createUnregisteredPlate(createUnregisteredPlateRequest()));
        unregisteredLicensePlates.add(createUnregisteredPlate(createUnregisteredPlateRequest()));
        return unregisteredLicensePlates;
    }

    private List<Long> createUnregisteredPlateIds() {
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        return ids;
    }
}
