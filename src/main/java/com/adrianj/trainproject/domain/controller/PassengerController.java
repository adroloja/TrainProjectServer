package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.services.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/getAllPassengers")
    public ResponseEntity<?> getAllsPassenger(){

        return ResponseEntity.ok(passengerService.getAllPassenger());
    }

    @PutMapping("/updatePassenger")
    public ResponseEntity<?> updatePassenger(@RequestBody Passenger passenger){

        return passengerService.updatePassenger(passenger);
    }

    @DeleteMapping("/deletePassenger/{id}")
    public ResponseEntity<?> deletePassenger(@PathVariable("id") long id){

        return passengerService.deletePassenger(id);
    }
}
