package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class Login {

    private PassengerRepository passengerRepository;

    @Autowired
    public Login(PassengerRepository passengerRepository){

        this.passengerRepository = passengerRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<String> getLogin(@RequestBody Passenger passenger){

        Optional<Passenger> optionalPassenger = passengerRepository.findByUsername(passenger.getUsername());

        if(optionalPassenger.isPresent()){

            Passenger passenger1 = optionalPassenger.get();

            if(passenger.getPassword().equals(passenger1.getPassword())){

                ObjectMapper objectMapper = new ObjectMapper();
                String response;

                try{

                    response = objectMapper.writeValueAsString(passenger1);

                } catch (JsonProcessingException e) {

                    return ResponseEntity.status(500).body("Error to create response");
                }
                return ResponseEntity.ok(response);

            }else{

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The password is not correct, please try again");
            }
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The username is not exists, please try again");
        }
    }
}
