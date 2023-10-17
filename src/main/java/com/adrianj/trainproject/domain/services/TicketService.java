package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<Ticket> getTicketByDay(int trainNumber, String date){

        List<Ticket> tickets = ticketRepository.getTicketByDay(date).get();
        List<Ticket> resultList = new ArrayList<>();

        for(Ticket t : tickets){

            if(t.getStartStops().getTrainStops().getNumber() == trainNumber){

                resultList.add(t);
            }
        }

        return resultList;
    }

    public ResponseEntity<?> updateTicket(Ticket ticket){

        Optional<Ticket> optionalTicket = ticketRepository.findById(ticket.getId());

        if(optionalTicket.isPresent()){

            ticketRepository.save(ticket);

            return ResponseEntity.ok("Update completed.");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The ticket doesn´t exist.");
        }
    }

    public List<Ticket> getAllTickets(){

        return (List<Ticket>) ticketRepository.findAll();
    }

    public ResponseEntity<?> deleteTicket(long id){

        if(ticketRepository.findById(id).isPresent()){

            ticketRepository.delete(ticketRepository.findById(id).get());

            return ResponseEntity.ok("Delete completed");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The ticket doesn´t exist.");
        }
    }

    public ResponseEntity<?> getTicketByPassengerAndDay(RequestTicketPassengerDay requestTicketPassengerDay) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date date = simpleDateFormat.parse(requestTicketPassengerDay.getDay());

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        Date date1 = simpleDateFormat.parse(requestTicketPassengerDay.getDay());

        date1.setHours(23);
        date1.setMinutes(59);
        date1.setSeconds(59);

        return ResponseEntity.ok( ticketRepository.getListTicketByIdAndDay(requestTicketPassengerDay.getIdPassenger(), date, date1));
    }

    public ResponseEntity<?> getPassangerTrain(RequestGetPassengerTrain requestGetPassengerTrain) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat fullsimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date date = simpleDateFormat.parse(requestGetPassengerTrain.getDay());

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        String startDate = fullsimpleDateFormat.format(date);

        Date date1 = simpleDateFormat.parse(requestGetPassengerTrain.getDay());

        date1.setHours(23);
        date1.setMinutes(59);
        date1.setSeconds(59);

        String endDate = fullsimpleDateFormat.format(date1);

        System.out.println("Train: " + requestGetPassengerTrain.getTrainNumber());

        Optional<List<Ticket>> optionalTicketList = ticketRepository.getListPassenger(requestGetPassengerTrain.getTrainNumber(), date, date1);

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

    public static class RequestTicketPassengerDay{

        long idPassenger;
        String day;

        public long getIdPassenger() {
            return idPassenger;
        }

        public void setIdPassenger(long idPassenger) {
            this.idPassenger = idPassenger;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }
    }

    public static class RequestGetPassengerTrain {

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

    public static class requestAllTicket{

        private String date;
        private int trainNumber;

        public int getTrainNumber() {
            return trainNumber;
        }

        public void setTrainNumber(int trainNumber) {
            this.trainNumber = trainNumber;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
