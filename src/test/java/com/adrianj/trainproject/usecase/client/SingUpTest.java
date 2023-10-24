package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import com.adrianj.trainproject.domain.services.PassengerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class SingUpTest {

    @Mock
    private PassengerRepository passengerRepository;
    @InjectMocks
    private PassengerService passengerService;

    private Passenger passenger;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        passenger = new Passenger();
        passenger.setUsername("prueba");
        passenger.setPassword("1234");

    }
    @Test
    void registrer() {

        when(passengerRepository.findByUsername(passenger.getUsername())).thenReturn(Optional.empty());

        ResponseEntity<?> response = passengerService.createPassenger(passenger);

        assertEquals(HttpStatus.OK, response.getStatusCode());


        // User existing

        when(passengerRepository.findByUsername(passenger.getUsername())).thenReturn(Optional.of(passenger));

        response = passengerService.createPassenger(passenger);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("The username is already exist. Please try another username", response.getBody());
    }
}