package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.repositories.TicketRepository;
import com.adrianj.trainproject.domain.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @GetMapping("/getAllTicket")
    public ResponseEntity<?> getAllTickets(){

        return ResponseEntity.ok(ticketService.getAllTickets());
    }

    @PostMapping("/getAllTicketByDate")
    public ResponseEntity<?> getAllTicketByDate(@RequestBody TicketService.requestAllTicket date){

        return ResponseEntity.ok(ticketService.getTicketByDay(date.getTrainNumber(), date.getDate()));
    }

    @PutMapping("/updateTicket")
    public ResponseEntity<?> updateTicket(@RequestBody Ticket ticket){

        return ticketService.updateTicket(ticket);
    }

    @DeleteMapping("/deleteTicket/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable long id){

        return ticketService.deleteTicket(id);
    }

    @PostMapping("/getTicketByIdAndDay")
    public ResponseEntity<?> getTickectByIdandDay(@RequestBody TicketService.RequestTicketPassengerDay requestTicketPassengerDay) throws ParseException {

        return ticketService.getTicketByPassengerAndDay(requestTicketPassengerDay);
    }

    @GetMapping("/getTicket/{id}")
    public ResponseEntity<?> getTicketById(@PathVariable long id){

        return ResponseEntity.ok(ticketService.getTicketById(id));
    }

}
