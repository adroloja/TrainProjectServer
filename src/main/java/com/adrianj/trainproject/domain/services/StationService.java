package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import lombok.RequiredArgsConstructor;
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

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The station doesn´t exist");
        }
    }

    public ResponseEntity<?> getStations(){

        return ResponseEntity.ok(stationRepository.findAll());
    }

    public ResponseEntity<?> updateStation(Station station){

        Optional<Station> optionalStation = stationRepository.findById(station.getId());

        if(optionalStation.isPresent()){

            Station t = optionalStation.get();
            t.setName(station.getName());

            stationRepository.save(t);

            return ResponseEntity.ok("Update completed");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The station doesn´t exist.");
        }
    }

    public ResponseEntity<?> createStation(Station station){

        Optional<Station> optionalStation = stationRepository.findByName(station.getName());

        if(optionalStation.isEmpty()){

            stationRepository.save(optionalStation.get());

            return ResponseEntity.ok(station);
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The station is already exist.");
        }
    }

    public ResponseEntity<?> deleteStation(long id){

        Optional<Station> optionalStation = stationRepository.findById(id);

        if(optionalStation.isPresent()){

            stationRepository.delete(optionalStation.get());

            return ResponseEntity.ok("Delete completed");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The station doesn´t exist");
        }
    }

}
