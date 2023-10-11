package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import com.adrianj.trainproject.domain.repositories.TicketRepository;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
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
public class ViewPassengerTrain {

    private TrainRepository trainRepository;
    private ScheduleRepository scheduleRepository;
    private TicketRepository ticketRepository;

    @Autowired
    public ViewPassengerTrain(TrainRepository trainRepository, ScheduleRepository scheduleRepository, TicketRepository ticketRepository){

        this.trainRepository = trainRepository;
        this.scheduleRepository = scheduleRepository;
        this.ticketRepository = ticketRepository;
    }

    @PostMapping("/getPassengerTrain")
    public ResponseEntity<?> getPassangerTrain(@RequestBody RequestGetPassengerTrain requestGetPassengerTrain) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Optional<List<Ticket>> optionalTicketList = ticketRepository.getListPassenger(requestGetPassengerTrain.getTrainNumber(), requestGetPassengerTrain.getDay());

        List<Passenger> passengerList;

        if(optionalTicketList.isPresent()){

            List<Ticket> ticketList = optionalTicketList.get();

            passengerList = new ArrayList<>();

            ticketList.forEach( ticket -> {

                passengerList.add(ticket.getPassenger());
            });

            return ResponseEntity.ok(passengerList);

        }else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("There haven´t been any passenger registred in this train yet");
        }
    }
    private static class RequestGetPassengerTrain {

        private int trainNumber;
        private String day;

        public RequestGetPassengerTrain(int trainNumber, String day) {
            this.trainNumber = trainNumber;
            this.day = day;
        }

        public RequestGetPassengerTrain() {
        }

        public int getTrainNumber() {
            return trainNumber;
        }

        public void setTrainNumber(int trainNumber) {
            this.trainNumber = trainNumber;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }
}


