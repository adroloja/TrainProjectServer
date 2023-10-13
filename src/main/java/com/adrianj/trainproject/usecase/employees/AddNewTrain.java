package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import com.adrianj.trainproject.domain.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AddNewTrain {

    private final TrainService trainService;

    @PostMapping("/addTrain")
    public ResponseEntity<?> addTrain(@RequestBody Train train){

        return trainService.createTrain(train);
    }
}


/*

 Optional<Train> optionalTrain = trainRepository.findByNumber(train.getNumber());

        if(optionalTrain.isEmpty()){

            trainRepository.save(train);

            return ResponseEntity.ok("ok");
        }else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error, the number train already exists.");
        }
 */