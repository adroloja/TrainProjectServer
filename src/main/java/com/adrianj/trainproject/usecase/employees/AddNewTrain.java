package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AddNewTrain {

    private TrainRepository trainRepository;

    @Autowired
    public AddNewTrain(TrainRepository trainRepository){

        this.trainRepository = trainRepository;
    }

    @PostMapping("/addTrain")
    public ResponseEntity<String> addTrain(@RequestBody Train train){

        Optional<Train> optionalTrain = trainRepository.findByNumber(train.getNumber());

        if(optionalTrain.isEmpty()){

            trainRepository.save(train);

            return ResponseEntity.ok("ok");
        }else {

            return ResponseEntity.status(500).body("Error, the number train already exists.");
        }
    }
}
