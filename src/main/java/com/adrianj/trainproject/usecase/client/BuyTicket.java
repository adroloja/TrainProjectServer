package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
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

    @Autowired
    public BuyTicket(TicketRepository ticketRepository, PassengerRepository passengerRepository, TrainRepository trainRepository){

        this.ticketRepository = ticketRepository;
        this.passengerRepository = passengerRepository;
        this.trainRepository = trainRepository;
    }

    @PostMapping("/buyTicket")
    public ResponseEntity<?> buy(@RequestBody RequestBuyTicket requestBuyTicket) throws ParseException {

        //
        // Firstly, Check max seats and if there are available some seats.
        //

        Train train = trainRepository.findByNumber(Integer.parseInt(requestBuyTicket.getTrainNumber())).get();
        int maxSeat = train.getSeats();

        List<Ticket> ticketList = ticketRepository.getListPassenger(Integer.toString(train.getNumber()), requestBuyTicket.getDay()).get();

        int numberPassenger = ticketList.size();

        // If there arent available seats
        if(numberPassenger >= maxSeat)  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No available seats. Thanks");

        //
        // Secondly, Check if the passenger is already registred.
        //

        Passenger passenger = passengerRepository.findById(Long.parseLong(requestBuyTicket.getIdPassenger())).get();

        boolean registred = false;

        for(Ticket t : ticketList){

            if(t.getId() == passenger.getId()){     // I have not checked another field like birth, name, etc. because se object Passenger is the same in this case.

                registred = true;
            }
        }

        if(registred) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The passenger is already registred in this train. Thanks");


        //
        // Now, Check if the current time is more than 10 min of difference with the train left
        //

        Date date = new Date();
        SimpleDateFormat simpleDateFormatTime = new SimpleDateFormat("HH:mm");
        SimpleDateFormat simpleDateFormatDate = new SimpleDateFormat("dd/MM/yyyy");

        String currentTimeString = simpleDateFormatTime.format(date);
        String currentDate = simpleDateFormatDate.format(date);

        if(currentDate.equals(requestBuyTicket.getDay())){

            String trainTimeString = requestBuyTicket.getTime();
            Date trainTime = simpleDateFormatTime.parse(trainTimeString);
            Date currentTime = simpleDateFormatTime.parse(currentTimeString);

            long difference = Math.abs(trainTime.getTime() - currentTime.getTime());

            long diffMin = difference / (60 * 1000);

            if (diffMin <= 10 ) {  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Sorry, You can´t buy the ticket, the train leaves in less than 10 minutes. Thanks");  }

        }

        //
        // If all is OK, create the ticket
        //

        Ticket ticket = new Ticket();
        Date timeTicket = simpleDateFormatTime.parse(requestBuyTicket.getTime());
        ticket.setTime(timeTicket);
        ticket.setDay(requestBuyTicket.getDay());
        ticket.setPassenger(passenger);
        ticket.setTrain(train);

        ticketRepository.save(ticket);

        return ResponseEntity.ok(ticket);

    }
}

class RequestBuyTicket{

    private String trainNumber;
    private String day;

    private String time;
    private String idPassenger;

    public String getTrainNumber() {
        return trainNumber;
    }

    public void setTrainNumber(String trainNumber) {
        this.trainNumber = trainNumber;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getIdPassenger() {
        return idPassenger;
    }

    public void setIdPassenger(String idPassenger) {
        this.idPassenger = idPassenger;
    }
}
