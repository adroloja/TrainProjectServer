package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Schedule;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;

    public ResponseEntity<?> getAllSchedule(){

        return ResponseEntity.ok((List<Schedule>) scheduleRepository.findAll());
    }

    public ResponseEntity<?> updateSchedule(ScheduleUpdate_DTO request){

        Schedule schedule1 = scheduleRepository.findById(request.getId()).orElseThrow();
        Train train = trainRepository.findById((long)request.trainNumber).orElseThrow();
        schedule1.setTrain(train);

        scheduleRepository.save(schedule1);

        return ResponseEntity.ok("{\"message\": \"Update completed successfully.\"}");
    }


    public ResponseEntity<?> insertSchedule(ScheduleInsert_DTO request){

        Train train = this.trainRepository.findByNumber(request.getTrainNumber()).orElseThrow();

        Schedule schedule = new Schedule();
        schedule.setTrain(train);

        this.scheduleRepository.save(schedule);

        return ResponseEntity.ok("{\"message\": \"Add successed\"}");
    }

    public ResponseEntity<?> deleteSchedule(long id){

        Schedule schedule = scheduleRepository.findById(id).orElseThrow();
        try{
            scheduleRepository.delete(schedule);
        }catch (DataIntegrityViolationException e){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"Unable to delete the schedule. It is associated with a train.\"}");
        }


        return ResponseEntity.ok("{\"message\": \"Delete completed successfully.\"}");
    }


    public static class ScheduleInsert_DTO{

        private int trainNumber;

        public int getTrainNumber() {
            return trainNumber;
        }

        public void setTrainNumber(int trainNumber) {
            this.trainNumber = trainNumber;
        }
    }

    public static class ScheduleUpdate_DTO{

        private int trainNumber;
        private long id;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public int getTrainNumber() {
            return trainNumber;
        }

        public void setTrainNumber(int trainNumber) {
            this.trainNumber = trainNumber;
        }
    }
}
