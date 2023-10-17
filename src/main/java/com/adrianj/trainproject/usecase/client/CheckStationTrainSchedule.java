package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
import com.adrianj.trainproject.domain.services.StationService;
import com.adrianj.trainproject.domain.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CheckStationTrainSchedule {

   private final StopsRepository stopsRepository;
   private final StationRepository stationRepository;
   private final TrainService trainService;

    @PostMapping("/checktrain")
    public ResponseEntity<?> getTrainSchedule(@RequestBody TrainService.TrainRequest trainRequest){

        return this.trainService.getStationStops(trainRequest);
    }

    @PostMapping("/checktrainByDay")
    public ResponseEntity<?> getTrainScheduleByDay(@RequestBody TrainService.TrainRequest trainRequest) throws ParseException {

        return this.trainService.getStationStopsByDay(trainRequest);
    }



}
