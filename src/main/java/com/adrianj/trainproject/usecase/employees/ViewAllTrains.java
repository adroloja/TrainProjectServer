package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ViewAllTrains {

    private final TrainRepository trainRepository;
    @GetMapping("/getAllTrain")
    public ResponseEntity<List<Train>> getAllTrain(){

        List<Train> trainList = (List<Train>) trainRepository.findAll();

        return ResponseEntity.ok(trainList);
    }
}
