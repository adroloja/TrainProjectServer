package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository stationRepository;

    public ResponseEntity<?> getStation(long id){

        Optional<Station> optionalStation = stationRepository.findById(id);

        if(optionalStation.isPresent()){

            return ResponseEntity.ok(optionalStation.get());

        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"The station doesn´t exist.\"}");
        }
    }

    public ResponseEntity<?> getStations(){

        return ResponseEntity.ok(stationRepository.findAll());
    }

    public ResponseEntity<?> updateStation(Station station) throws JsonProcessingException {

        Optional<Station> optionalStation = stationRepository.findById(station.getId());

        if(optionalStation.isPresent()){

            Station t = optionalStation.get();
            t.setName(station.getName());

            stationRepository.save(t);

            return ResponseEntity.ok("{\"message\": \"Update completed\"}");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"The station doesn´t exist\"}");
        }
    }

    public ResponseEntity<?> createStation(Station station){

        Optional<Station> optionalStation = stationRepository.findByName(station.getName());

        if(optionalStation.isEmpty()){

            stationRepository.save(station);

            return ResponseEntity.ok(station);
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"The station is already exist\"}");
        }
    }

    public ResponseEntity<?> deleteStation(long id){

        Optional<Station> optionalStation = stationRepository.findById(id);

        if(optionalStation.isPresent()){

           try{
               stationRepository.delete(optionalStation.get());

           }catch (DataIntegrityViolationException e){

               return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"Unable to delete the Station. It is associated with tickets/stops.\"}");
           }

            return ResponseEntity.ok("{\"message\": \"Delete completed\"}");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"The station doesn´t exist\"}");
        }
    }

}
