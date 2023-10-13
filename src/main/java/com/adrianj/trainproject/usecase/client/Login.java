package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.config.jwt.JwtUtils;
import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class Login {

    private final PassengerRepository passengerRepository;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> getLogin(@RequestBody Passenger passenger){

        Optional<Passenger> optionalPassenger = passengerRepository.findByUsername(passenger.getUsername());

        if(optionalPassenger.isPresent()){

            Passenger passenger1 = optionalPassenger.get();

            if(passenger.getPassword().equals(passenger1.getPassword())){
                String token = jwtUtils.generateToken(passenger1.getUsername());

                UserDto userDto = new UserDto();
                userDto.setPassenger(passenger1);
                userDto.setToken(token);

                return ResponseEntity.ok(userDto);

            }else{

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The password is not correct, please try again");
            }
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The username is not exist, please try again");
        }
    }

    public static class UserDto {

        private Passenger passenger;
        private String token;

        public Passenger getPassenger() {
            return passenger;
        }

        public void setPassenger(Passenger passenger) {
            this.passenger = passenger;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
