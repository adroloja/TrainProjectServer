package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.entities.Schedule;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ScheduleController {

    private ScheduleRepository scheduleRepository;

    @Autowired
    public ScheduleController(ScheduleRepository scheduleRepository){

        this.scheduleRepository = scheduleRepository;
    }

    @PostMapping("/insertSchedule")
    public ResponseEntity<String> insertSchedule(@RequestBody Schedule schedule){

        Schedule schedule1 = scheduleRepository.save(schedule);

        return ResponseEntity.ok("Ok");
    }

}
