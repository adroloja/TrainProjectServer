package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.*;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
import com.adrianj.trainproject.domain.repositories.TicketRepository;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import com.adrianj.trainproject.domain.services.EmailService;
import com.adrianj.trainproject.domain.services.TicketService;
import com.adrianj.trainproject.usecase.employees.AddNewStation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuyTicketTest {

    @Mock
    TicketRepository ticketRepository;

    @Mock
    PassengerRepository passengerRepository;

    @Mock
    TrainRepository trainRepository;

    @Mock
    StopsRepository stopsRepository;

    @Mock
    TicketService ticketService;

    private BuyTicket.RequestBuyTicket requestBuyTicket;
    private Train train;
    private Stops startStops;
    private Passenger passenger;
    private Schedule schedule;
    private Station station;
    @BeforeEach
    void setUp() throws ParseException {

        MockitoAnnotations.initMocks(this);

        passenger = new Passenger();
        passenger.setId(11);
        passenger.setName("prueba");
        passenger.setUsername("prueba");
        passenger.setEmploye(false);
        passenger.setSurname("prueba");


        requestBuyTicket = new BuyTicket.RequestBuyTicket();
        requestBuyTicket.setTrainNumber(1);
        requestBuyTicket.setIdStartStops(1l);
        requestBuyTicket.setIdEndStops(3l);
        requestBuyTicket.setIdPassenger(28l);

        train = new Train();
        train.setNumber(1);
        train.setSeats(100);

        schedule = new Schedule();
        schedule.setTrain(train);
        schedule.setId(1);

        station = new Station();
        station.setName("Málaga");
        station.setId(1);

        startStops = new Stops();
        startStops.setId(1);
        startStops.setTrainStops(train);
        startStops.setSchedule(schedule);
        startStops.setStationStop(station);
        String time = "2023-10-08 10:30:00.000000";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
        startStops.setTime(simpleDateFormat.parse(time));

    }

    @Test
    void buy() throws ParseException {

        when(trainRepository.findByNumber(anyInt())).thenReturn(Optional.of(train));
        when(stopsRepository.findById(anyLong())).thenReturn(Optional.of(startStops));
        when(passengerRepository.findById(anyLong())).thenReturn(Optional.of(passenger));
        when(ticketService.getTicketByDay(anyInt(), anyString())).thenReturn(new ArrayList<>());
        when(ticketRepository.getListPassenger(anyInt(), any(Date.class), any(Date.class))).thenReturn(Optional.of(new ArrayList<>()));

        EmailService emailService = mock(EmailService.class);
        BuyTicket buyTicket = new BuyTicket(ticketRepository, passengerRepository, trainRepository,stopsRepository,ticketService,emailService);

        ResponseEntity<?> response = buyTicket.buy(requestBuyTicket);


        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());


        verify(trainRepository, times(1)).findByNumber(anyInt());
        verify(stopsRepository, times(2)).findById(anyLong());
        verify(passengerRepository, times(1)).findById(anyLong());
        verify(ticketService, times(1)).getTicketByDay(anyInt(), anyString());
        verify(ticketRepository, times(1)).getListPassenger(anyInt(), any(Date.class), any(Date.class));
    }
}