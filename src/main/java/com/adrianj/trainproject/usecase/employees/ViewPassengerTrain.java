package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ViewPassengerTrain {

    private TrainRepository trainRepository;
    private ScheduleRepository scheduleRepository;

    @Autowired
    public ViewPassengerTrain(TrainRepository trainRepository, ScheduleRepository scheduleRepository){

        this.trainRepository = trainRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @PostMapping("/getPassengerTrain")
    public ResponseEntity<List<Passenger>> getPassangerTrain()
}
