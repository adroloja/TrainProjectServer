package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AddNewStation {

    private StationRepository stationRepository;

    @Autowired
    public AddNewStation(StationRepository stationRepository){

        this.stationRepository = stationRepository;
    }

    @PostMapping("/addStation")
    public ResponseEntity<String> addStation(@RequestBody Station station){

        Optional<Station> optionalStation = stationRepository.findByName(station.getName());

        if(optionalStation.isEmpty()){

            stationRepository.save(station);

            return ResponseEntity.ok("ok");

        }else {

            return ResponseEntity.status(500).body("The station already exists.");
        }
    }
}
