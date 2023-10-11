package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
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
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@RestController
public class BuyTicket {

    private TicketRepository ticketRepository;
    private PassengerRepository passengerRepository;
    private TrainRepository trainRepository;
    private StopsRepository stopsRepository;

    @Autowired
    public BuyTicket(TicketRepository ticketRepository, PassengerRepository passengerRepository, TrainRepository trainRepository, StopsRepository stopsRepository){

        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.trainRepository = trainRepository;
        this.stopsRepository = stopsRepository;
    }

    @PostMapping("/buyTicket")
    public ResponseEntity<?> buy(@RequestBody RequestBuyTicket requestBuyTicket) throws ParseException {

        //
        // Firstly, Check max seats and if there are available some seats.
        //

        Train train = trainRepository.findByNumber(Integer.parseInt(requestBuyTicket.getTrainNumber())).get();
        int maxSeat = train.getSeats();

        Stops startStops = stopsRepository.findById(requestBuyTicket.getIdStartStops()).get();

        Date time = startStops.getTime();

        List<Ticket> ticketList = ticketRepository.getListPassenger(train.getNumber(), time.toString()).get();

        int numberPassenger = ticketList.size();

        // If there aren´t available seats
        if (numberPassenger >= maxSeat)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No available seats. Thanks");

        //
        // Secondly, Check if the passenger is already registred.
        //

        Passenger passenger = passengerRepository.findById(Long.parseLong(requestBuyTicket.getIdPassenger())).get();

        boolean registred = false;

        for (Ticket t : ticketList) {

            if (t.getId() == passenger.getId()) {     // I have not checked another field like birth, name, etc. because se object Passenger is the same in this case.

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

        Stops endStops = stopsRepository.findById(requestBuyTicket.getIdEndStops()).get();

        Ticket ticket = new Ticket();
        ticket.setPassenger(passenger);
        ticket.setStartStops(startStops);
        ticket.setEndStops(endStops);

        ticketRepository.save(ticket);

        return ResponseEntity.ok(ticket);
    }
    private class RequestBuyTicket{

        private String trainNumber;
        private long idStartStops;
        private long idEndStops;
        private String idPassenger;

        public String getTrainNumber() {
            return trainNumber;
        }

        public void setTrainNumber(String trainNumber) {
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

        public String getIdPassenger() {
            return idPassenger;
        }

        public void setIdPassenger(String idPassenger) {
            this.idPassenger = idPassenger;
        }
    }

}

