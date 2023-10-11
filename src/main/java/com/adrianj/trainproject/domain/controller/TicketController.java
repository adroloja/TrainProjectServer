package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.repositories.TicketRepository;
import com.adrianj.trainproject.domain.services.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/getAllTicket")
    public ResponseEntity<?> getAllTicketByDate(@RequestBody requestAllTicket date){

        return ResponseEntity.ok(ticketService.getTicketByDay(date.getTrainNumber(), date.getDate()));
    }

    private static class requestAllTicket{

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
