package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import com.adrianj.trainproject.domain.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;
    private final TrainRepository trainRepository;


    @GetMapping("/getTrain/{id}")
    public ResponseEntity<?> getTrain(@PathVariable long id){

        return trainService.getTrain(id);
    }

    @GetMapping("/getAllTrains")
    public ResponseEntity<?> getAllTrain(){

        return ResponseEntity.ok(trainRepository.findAll());
    }

    @DeleteMapping("/deleteTrain/{id}")
    public ResponseEntity<?> deleteTrain(@PathVariable long id){

        return trainService.deleteTrain(id);
    }

    @PutMapping("/updateTrain")
    public ResponseEntity<?> updateTrain(@RequestBody Train train){

        return trainService.updateTrain(train);
    }
 }
