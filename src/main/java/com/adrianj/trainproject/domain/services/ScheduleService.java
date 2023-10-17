package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Schedule;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ResponseEntity<?> getAllSchedule(){

        return ResponseEntity.ok((List<Schedule>) scheduleRepository.findAll());
    }


    public ResponseEntity<?> insertSchedule(Schedule schedule){

        this.scheduleRepository.save(schedule);

        return ResponseEntity.ok("{\"message\": \"Add successed\"}");
    }
}
