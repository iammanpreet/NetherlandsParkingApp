package com.assignment.parking.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.assignment.parking.model.request.RegisterParkingRequest;
import com.assignment.parking.model.request.UnregisterParkingRequest;
import com.assignment.parking.model.response.BaseResponse;
import com.assignment.parking.service.ParkingService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.*;

@AutoConfigureMockMvc
@SpringBootTest
public class ParkingControllerMVCTest{

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ParkingService parkingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterParkingSession() throws Exception {
        RegisterParkingRequest registerParkingRequest = new RegisterParkingRequest("License1","Street1");

        BaseResponse response = new BaseResponse("Parking session started successfully for license plate number: License1");
        doNothing().when(parkingService).registerParkingSession(registerParkingRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/parking/registerParkingSession")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerParkingRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(response.getMessage()));
    }

    @Test
    void testRegisterParkingSession_InvalidLicensePlate() throws Exception {
        RegisterParkingRequest registerParkingRequest = new RegisterParkingRequest("","Street1");

        BaseResponse response = new BaseResponse("Parking session started successfully for license plate number: License1");
        doNothing().when(parkingService).registerParkingSession(registerParkingRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/parking/registerParkingSession")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerParkingRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void testRegisterParkingSession_InvalidStreet() throws Exception {
        RegisterParkingRequest registerParkingRequest = new RegisterParkingRequest("License1","");

        BaseResponse response = new BaseResponse("Parking session started successfully for license plate number: License1");
        doNothing().when(parkingService).registerParkingSession(registerParkingRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/parking/registerParkingSession")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerParkingRequest)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    @Test
    void testUnregisterParkingSession() throws Exception {
        UnregisterParkingRequest unregisterParkingRequest = new UnregisterParkingRequest();
        unregisterParkingRequest.setLicensePlateNumber("License1");
        BaseResponse response = new BaseResponse("Parking session ended successfully for license plate number: License1");
        doNothing().when(parkingService).unregisterParkingSession(unregisterParkingRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/parking/unregisterParkingSession")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(unregisterParkingRequest)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
