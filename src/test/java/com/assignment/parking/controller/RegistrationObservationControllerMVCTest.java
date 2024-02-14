package com.assignment.parking.controller;

import com.assignment.parking.model.request.RegistrationObservationRequest;
import com.assignment.parking.service.RegistrationObservationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
@AutoConfigureMockMvc
@SpringBootTest
public class RegistrationObservationControllerMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private RegistrationObservationService registrationObservationService;

    @Test
    void testSaveRegistrationObservations() throws Exception {
        List<RegistrationObservationRequest> observations = Collections.singletonList(
                new RegistrationObservationRequest("Licens1","Street1", LocalDateTime.now().minusDays(2))
        );
        Mockito.doNothing().when(registrationObservationService).saveRegistrationObservations(Mockito.anyList());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/observation/saveRegistrationObservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(observations)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Registrations Observations saved successfully."));
    }
}
