package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Schedule;
import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class CheckStationTrainSchedule {

   private StopsRepository stopsRepository;
   private StationRepository stationRepository;
    @Autowired
    public CheckStationTrainSchedule(StopsRepository stopsRepository, StationRepository stationRepository){

        this.stopsRepository = stopsRepository;
        this.stationRepository = stationRepository;
    }

    @PostMapping("/checktrain")
    public ResponseEntity<?> getTrainSchedule(@RequestBody TrainRequest trainRequest){

        String stationName = trainRequest.getName();

        Optional<Station> optionalStation = stationRepository.findByName(stationName);

        if(optionalStation.isPresent()){

            Optional<List<Stops>> optionalStops = stopsRepository.getOneStationTrainStopById(optionalStation.get().getId());

            if(optionalStops.isPresent()){

                return ResponseEntity.ok(optionalStops.get());

            }else{

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("There isn´t any stops in this station. Thanks");
            }

        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The station doesn´t exists. Thanks");
        }
    }


    private static class TrainRequest{

        private String name;

        public TrainRequest(String station) {
            this.name = station;

        }

        public TrainRequest() {
        }

        public String getName() {
            return name;
        }

        public void setName(String station) {
            this.name = station;
        }

    }
}
