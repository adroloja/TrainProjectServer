package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.services.TicketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.text.ParseException;

import java.util.ArrayList;
import java.util.List;

class ViewPassengerTrainTest {


    private TicketService.RequestGetPassengerTrain requestGetPassengerTrain;
    private List<Passenger> passengerList;
    @BeforeEach
    void setUp() {

        requestGetPassengerTrain = new TicketService.RequestGetPassengerTrain();
        requestGetPassengerTrain.setTrainNumber(1);
        requestGetPassengerTrain.setDay("2023/10/08");

        passengerList = new ArrayList<>();
        Passenger passenger = new Passenger();
        passenger.setId(2);

        passengerList.add(passenger);
    }

    @Test
    void getPassangerTrain() throws ParseException {

        TicketService ticketService = Mockito.mock(TicketService.class);
        ViewPassengerTrain viewPassengerTrain = new ViewPassengerTrain(ticketService);


        ResponseEntity<?> expectedResponse = ResponseEntity.ok(passengerList);

        Mockito.<ResponseEntity<?>>when(ticketService.getPassangerTrain(requestGetPassengerTrain)).thenReturn(expectedResponse);

        ResponseEntity<?> actualResponse = viewPassengerTrain.getPassangerTrain(requestGetPassengerTrain);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }
}