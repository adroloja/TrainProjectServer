package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import com.adrianj.trainproject.domain.services.PassengerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class SingUp {

    private final PassengerService passengerService;

    @PostMapping("/registrer")
    public ResponseEntity<?> registrer(@RequestBody Passenger passenger){

      return passengerService.createPassenger(passenger);    }
}

/*
  Optional<Passenger> optionalPassenger = passengerRepository.findByUsername(passenger.getUsername());

        if(optionalPassenger.isEmpty()){

            passengerRepository.save(passenger);

            ObjectMapper objectMapper = new ObjectMapper();
            String response;

            try{

                response = objectMapper.writeValueAsString(passenger);

            } catch (JsonProcessingException e) {

                return ResponseEntity.status(500).body("Error to convert the passenger to Json");
            }

            return ResponseEntity.ok(response);

        }else{

            return  ResponseEntity.ok("The username is already exist. Please try another username");
        }
 */