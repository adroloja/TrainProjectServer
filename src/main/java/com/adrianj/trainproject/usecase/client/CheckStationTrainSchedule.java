package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Schedule;
import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class CheckStationTrainSchedule {

    private TrainRepository trainRepository;
    private StationRepository stationRepository;
    private ScheduleRepository scheduleRepository;

    @Autowired
    public CheckStationTrainSchedule(TrainRepository trainRepository, StationRepository stationRepository, ScheduleRepository scheduleRepository){

        this.trainRepository = trainRepository;
        this.scheduleRepository = scheduleRepository;
        this.stationRepository = stationRepository;
    }

    @PostMapping("/checktrain")
    public ResponseEntity<List<Schedule>> getTrainSchedule(@RequestBody TrainRequest trainRequest){

        String stationName = trainRequest.getStation();

        Optional<Station> optionalStation = stationRepository.findByName(stationName);

        if(optionalStation.isPresent()){

            Optional<List<Schedule>> optionalSchedule = scheduleRepository.findAllByStation(optionalStation.get());

            if(optionalSchedule.isPresent()){

                return ResponseEntity.ok(optionalSchedule.get());

            }else{

                return ResponseEntity.noContent().build();
            }

        }else{

            return ResponseEntity.notFound().build();
        }
    }
}

class TrainRequest{

    private String station;

    public TrainRequest(String station) {
        this.station = station;

    }

    public TrainRequest() {
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

}
