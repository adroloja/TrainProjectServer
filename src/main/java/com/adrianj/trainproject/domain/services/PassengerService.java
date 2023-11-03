package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.config.jwt.JwtUtils;
import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final JwtUtils jwtUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public ResponseEntity<?> createPassenger(Passenger passenger){

        Optional<Passenger> optionalPassenger = passengerRepository.findByUsername(passenger.getUsername());

        if(optionalPassenger.isEmpty()){

            String password = bCryptPasswordEncoder.encode(passenger.getPassword());
            passenger.setPassword(password);

            passengerRepository.save(passenger);

            return ResponseEntity.ok(passenger);

        }else{

            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The username is already exist. Please try another username");
        }
    }

    public ResponseEntity<?> login(Passenger passenger){
        Optional<Passenger> optionalPassenger = passengerRepository.findByUsername(passenger.getUsername());

        if(optionalPassenger.isPresent()){

            Passenger passenger1 = optionalPassenger.get();
            String pass = bCryptPasswordEncoder.encode(passenger.getPassword());

            System.out.println(pass);
            System.out.println(passenger1.getPassword());
            if(bCryptPasswordEncoder.matches(pass, passenger1.getPassword())){
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

    public ResponseEntity<?> updatePassenger(Passenger passenger){

        if(passengerRepository.findById(passenger.getId()).isPresent()){

            passengerRepository.save(passenger);

            return ResponseEntity.ok("{\"message\": \"Update completed\"}");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"The Passenger doesn´t exist\"}");
        }
    }

    public ResponseEntity<?> deletePassenger(long id){

        if(passengerRepository.findById(id).isPresent()){

            passengerRepository.delete(passengerRepository.findById(id).get());

            return ResponseEntity.ok("{\"message\": \"Delete completed\"}");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"The Passenger doesn´t exist\"}");
        }
    }

    public List<Passenger> getAllPassenger(){

        return  (List<Passenger>) passengerRepository.findAll();
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
