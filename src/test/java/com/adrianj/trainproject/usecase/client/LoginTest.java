package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoginTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private Login login;

    private Passenger passenger;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        passenger = new Passenger();
        passenger.setUsername("adroyoyo");
        passenger.setPassword("1234");
    }

    @Test
    void getLogin() {
        // Mock the repository response
        when(passengerRepository.findByUsername(passenger.getUsername())).thenReturn(Optional.of(passenger));

        // Call the method under test
        ResponseEntity<?> response = login.getLogin(passenger);

        // Assert the response status and body
        assertEquals(HttpStatus.OK, response.getStatusCode());
        try {
            Passenger responsePassenger = new ObjectMapper().readValue((JsonParser) response.getBody(), Passenger.class);
            assertEquals(passenger.getUsername(), responsePassenger.getUsername());
            assertEquals(passenger.getPassword(), responsePassenger.getPassword());
        } catch (JsonProcessingException e) {
            fail("Error parsing response body");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Test with wrong password
        Passenger wrongPasswordPassenger = new Passenger();
        wrongPasswordPassenger.setUsername(passenger.getUsername());
        wrongPasswordPassenger.setPassword("12345");

        response = login.getLogin(wrongPasswordPassenger);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("The password is not correct, please try again", response.getBody());

        // Test with non-existing user
        when(passengerRepository.findByUsername("adroyoyoyo")).thenReturn(Optional.empty());

        Passenger nonExistingUserPassenger = new Passenger();
        nonExistingUserPassenger.setUsername("adroyoyoyo");
        nonExistingUserPassenger.setPassword("1234");

        response = login.getLogin(nonExistingUserPassenger);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("The username is not exist, please try again", response.getBody());
    }

}