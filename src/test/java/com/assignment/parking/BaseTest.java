package com.assignment.parking;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;

public class BaseTest {
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}
