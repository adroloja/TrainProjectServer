package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.repositories.TicketRepository;
import com.adrianj.trainproject.domain.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/getAllTicket")
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


}
