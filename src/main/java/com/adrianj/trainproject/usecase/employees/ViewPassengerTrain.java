package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import com.adrianj.trainproject.domain.repositories.TicketRepository;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import com.adrianj.trainproject.domain.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ViewPassengerTrain {

    private final TicketService ticketService;

    @PostMapping("/getPassengerTrain")
    public ResponseEntity<?> getPassangerTrain(@RequestBody TicketService.RequestGetPassengerTrain requestGetPassengerTrain) throws ParseException {

        return ticketService.getPassangerTrain(requestGetPassengerTrain);
    }

}


