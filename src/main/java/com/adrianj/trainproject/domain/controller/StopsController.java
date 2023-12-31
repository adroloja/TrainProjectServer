package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.services.StopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
public class StopsController {

    private final StopService stopService;

    @PostMapping("/getStopsTrainFromStartTime")
    public ResponseEntity<?> getStopsTrainFromStartTime(@RequestBody StopService.ResquestgetStopsTrainFromStartTime resquestgetStopsTrainFromStartTime) throws ParseException {

        return ResponseEntity.ok(stopService.getStopsTrainFromStartTime(resquestgetStopsTrainFromStartTime.getTrainNumber(), resquestgetStopsTrainFromStartTime.getStartTime()));
    }

    @PostMapping("/getStopsTrainsDay")
    public ResponseEntity<?> getStopsTrainDay(@RequestBody StopService.ResquestgetStopsTrainFromStartTime resquestgetStopsTrainFromStartTime) throws ParseException {

        return ResponseEntity.ok(stopService.getStopsTrainFromDay(resquestgetStopsTrainFromStartTime.getTrainNumber(), resquestgetStopsTrainFromStartTime.getStartTime()));
    }

    @PostMapping("/getStopsFromTimeStation")
    public ResponseEntity<?> getStopsFromTimeStation(@RequestBody StopService.RequestSearchTrain request) throws ParseException{

        return ResponseEntity.ok(stopService.getStopsFromAStationStopTime(request.getStationId1(), request.getStationId2(), request.getStartTime(), request.getEndTime()));
    }

    @PostMapping("/getStopsAllTrainByDay")
    public ResponseEntity<?> getStopsAllTrainByDay(@RequestBody StopService.ResquestgetStopsTrainFromStartTime request) throws ParseException {

        return ResponseEntity.ok(stopService.getStopsAllTrainByDay(request.getStartTime()));
    }
    @DeleteMapping("/deleteStops/{id}")
    public ResponseEntity<?> deleteStops(@PathVariable long id){

        return stopService.deleteStops(id);
    }

    @PutMapping("/updateStops")
    public ResponseEntity<?> updateStops(@RequestBody Stops stops){

        return stopService.updateStops(stops);
    }

    @PostMapping("/createStops")
    public ResponseEntity<?> createStops(@RequestBody StopService.RequestCreateStop requestCreateStop) throws ParseException {

        return stopService.createStop(requestCreateStop);
    }

    @GetMapping("/getAllStops")
    public ResponseEntity<?> getAllStops(){

        return stopService.getAllStops();
    }
}
