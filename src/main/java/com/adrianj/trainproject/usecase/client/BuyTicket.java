package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.controller.TicketController;
import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
@RequiredArgsConstructor
public class BuyTicket {

    private final TicketRepository ticketRepository;
    private final PassengerRepository passengerRepository;
    private final TrainRepository trainRepository;
    private final StopsRepository stopsRepository;
    private final TicketService ticketService;

    @PostMapping("/buyTicket")
    public ResponseEntity<?> buy(@RequestBody RequestBuyTicket requestBuyTicket) throws ParseException {

        //
        // Firstly, Check max seats and if there are available some seats.
        //

        Train train;
        int maxSeat;
        Optional<Train> optionalTrain = trainRepository.findByNumber(requestBuyTicket.getTrainNumber());
        if(optionalTrain.isPresent()){

            train = optionalTrain.get();
            maxSeat = train.getSeats();

        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The train doesn´t exist. Thanks.");
        }

        Stops startStops;
        Date time;
        Optional<Stops> optionalStops = stopsRepository.findById(requestBuyTicket.getIdStartStops());
        if(optionalStops.isPresent()){

            startStops = optionalStops.get();
            time = startStops.getTime();
        }else{

            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body("The stop doesn´t exist. Thanks.");
        }


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formatDate = simpleDateFormat.format(time);

        List<Ticket> tickets = ticketService.getTicketByDay(requestBuyTicket.getTrainNumber(), formatDate);

        int numberPassenger = tickets.size();

        // If there aren´t available seats
        if (numberPassenger >= maxSeat)
            return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body("No available seats. Thanks");

        //
        // Secondly, Check if the passenger is already registred.
        //

        Passenger passenger;
        Optional<Passenger> optionalPassenger = passengerRepository.findById(requestBuyTicket.getIdPassenger());
        if(optionalPassenger.isPresent()){

            passenger = optionalPassenger.get();
        }else{

            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("The passenger doesn´t exist. Thanks.");
        }
        Date startDateTime = new Date();
        Date endTime = new Date();

        startDateTime.setTime(startStops.getTime().getTime());
        startDateTime.setHours(0);
        startDateTime.setMinutes(0);

        endTime.setTime(startDateTime.getTime());
        endTime.setHours(23);
        endTime.setMinutes(59);

        List<Ticket> listPassengerTrain = ticketRepository.getListPassenger(requestBuyTicket.getTrainNumber(), startDateTime, endTime ).get();

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");


        Optional<List<Ticket>> optionalTicketticket = ticketRepository.getTicketByDayAndTrain(requestBuyTicket.trainNumber, requestBuyTicket.getIdPassenger() ,
                simpleDateFormat1.format(startDateTime));

        List<Ticket> ticketList = new ArrayList<>();
        if(optionalTicketticket.isPresent()){

            ticketList = optionalTicketticket.get();
        }

        boolean registred = false;

        for (Ticket t : ticketList) {

            if (t.getPassenger().getId() == passenger.getId()) {     // I have not checked another field like birth, name, etc. because se object Passenger is the same in this case.

                registred = true;
            }
        }

        if (registred)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The passenger is already registred in this train. Thanks");


        //
        // Now, Check if the current time is more than 10 min of difference with the train left
        //

        Date date = new Date();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd/MM/yyyy");

        String currentTimeString = simpleDateFormatTime.format(date);
        String currentDate = simpleDateFormatDate.format(date);

        String startDay = simpleDateFormatDate.format(time);
        String startTime = simpleDateFormatTime.format(time);

        if (currentDate.equals(startDay)) {

            Date trainTime = simpleDateFormatTime.parse(startTime);
            Date currentTime = simpleDateFormatTime.parse(currentTimeString);

            long difference = Math.abs(trainTime.getTime() - currentTime.getTime());

            long diffMin = difference / (60 * 1000);

            if (diffMin <= 10) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sorry, You can´t buy the ticket, the train leaves in less than 10 minutes. Thanks");
            }

        }

        //
        // If all is OK, create the ticket
        //

        Stops endStops;
        Ticket ticket = new Ticket();
        Optional<Stops> optionalStops1 = stopsRepository.findById(requestBuyTicket.getIdEndStops());
        if(optionalStops1.isPresent()){

            endStops = optionalStops1.get();
            ticket.setPassenger(passenger);
            ticket.setStartStops(startStops);
            ticket.setEndStops(endStops);
            ticket.setSeat(numberPassenger + 1);

        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The Passenger doesn´t exist. Thanks.");
        }

        ticketRepository.save(ticket);

        return ResponseEntity.ok(ticket);
    }
    public static class RequestBuyTicket{

        private int trainNumber;
        private long idStartStops;
        private long idEndStops;
        private long idPassenger;

        public int getTrainNumber() {
            return trainNumber;
        }

        public void setTrainNumber(int trainNumber) {
            this.trainNumber = trainNumber;
        }

        public long getIdStartStops() {
            return idStartStops;
        }

        public void setIdStartStops(long idStartStops) {
            this.idStartStops = idStartStops;
        }

        public long getIdEndStops() {
            return idEndStops;
        }

        public void setIdEndStops(long idEndStops) {
            this.idEndStops = idEndStops;
        }

        public long getIdPassenger() {
            return idPassenger;
        }

        public void setIdPassenger(long idPassenger) {
            this.idPassenger = idPassenger;
        }

        @Override
        public String toString() {
            return "RequestBuyTicket{" +
                    "trainNumber=" + trainNumber +
                    ", idStartStops=" + idStartStops +
                    ", idEndStops=" + idEndStops +
                    ", idPassenger=" + idPassenger +
                    '}';
        }
    }

}

