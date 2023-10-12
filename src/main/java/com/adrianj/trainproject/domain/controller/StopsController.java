package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.services.StopService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
public class StopsController {

    private final StopService stopService;

    @PostMapping("/getStopsTrainFromStartTime")
    public ResponseEntity<?> getStopsTrainFromStartTime(@RequestBody ResquestgetStopsTrainFromStartTime resquestgetStopsTrainFromStartTime) throws ParseException {

        return ResponseEntity.ok(stopService.getStopsTrainFromStartTime(resquestgetStopsTrainFromStartTime.getTrainNumber(), resquestgetStopsTrainFromStartTime.getStartTime()));
    }








    private static class ResquestgetStopsTrainFromStartTime {

        private int trainNumber;
        private String startTime;

        public int getTrainNumber() {
            return trainNumber;
        }

        public void setTrainNumber(int trainNumber) {
            this.trainNumber = trainNumber;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }
    }
}
