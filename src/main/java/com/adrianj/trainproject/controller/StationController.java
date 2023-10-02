package com.adrianj.trainproject.controller;

import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StationController {

    private StationRepository stationRepository;

    @Autowired
    public StationController(StationRepository stationRepository){

        this.stationRepository = stationRepository;
    }

    @GetMapping("/getStation")
    public ResponseEntity<List<Station>> getStations(){

        List<Station> listStation = (List<Station>) stationRepository.findAll();

        return ResponseEntity.ok(listStation);
    }


}
