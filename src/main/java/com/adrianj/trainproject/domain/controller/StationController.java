package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import com.adrianj.trainproject.domain.services.StationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @GetMapping("/getStations")
    public ResponseEntity<?> getStations(){

        return stationService.getStations();
    }

    @PutMapping("/updateStation")
    public ResponseEntity<?> updateStation(@RequestBody Station station) throws JsonProcessingException {

        return stationService.updateStation(station);
    }

    @DeleteMapping("/deleteStation/{id}")
    public ResponseEntity<?> deleteStation(@PathVariable long id){

        return stationService.deleteStation(id);
    }

}
