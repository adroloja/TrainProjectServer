package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.entities.Schedule;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import com.adrianj.trainproject.domain.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @GetMapping("/getAllSchedules")
    public ResponseEntity<?> getAllSchedule(){

        return scheduleService.getAllSchedule();
    }

    @PostMapping("/insertSchedule")
    public ResponseEntity<?> insertSchedule(@RequestBody ScheduleService.ScheduleInsert_DTO schedule){

        return scheduleService.insertSchedule(schedule);
    }

    @PutMapping("/updateSchedule")
    public ResponseEntity<?> updateSchedule(@RequestBody ScheduleService.ScheduleUpdate_DTO request){

        return scheduleService.updateSchedule(request);
    }

    @DeleteMapping("/deleteSchedule/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable long id){

        return scheduleService.deleteSchedule(id);
    }

}
