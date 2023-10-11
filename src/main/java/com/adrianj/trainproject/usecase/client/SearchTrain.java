package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import org.apache.tomcat.util.net.NioEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class SearchTrain {

    private StationRepository stationRepository;
    private TrainRepository trainRepository;
    private ScheduleRepository scheduleRepository;
    private StopsRepository stopsRepository;

    @Autowired
    public SearchTrain(StationRepository stationRepository, TrainRepository trainRepository, ScheduleRepository scheduleRepository, StopsRepository stopsRepository){

        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.scheduleRepository = scheduleRepository;
        this.stopsRepository = stopsRepository;
    }

    @PostMapping("/searchTrain")
    public ResponseEntity<?> search(@RequestBody RequestSearchTrain requestSearchTrain) {


        Date startDate = Date.from(requestSearchTrain.startTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(requestSearchTrain.getEndTime().atZone(ZoneId.systemDefault()).toInstant());

        System.out.println(requestSearchTrain.toString());
        Optional<List<Stops>> optionalStopsList = stopsRepository.getTrainsPassingBetweenStations(requestSearchTrain.getStationId1(), requestSearchTrain.getStationId2(),
                startDate, endDate);

        if(optionalStopsList.isPresent()){

            List<Stops> stopsList = optionalStopsList.get();

            List<Train> trainList = new ArrayList<>();

            for(Stops s : stopsList){

                trainList.add(s.getTrainStops());
            }

            return ResponseEntity.ok(stopsList);
        }

        return ResponseEntity.ok("ok");

    }

    private static class RequestSearchTrain{

        private long stationId1;
        private long stationId2;
        private LocalDateTime  startTime;
        private LocalDateTime  endTime;

        public long getStationId1() {
            return stationId1;
        }

        public void setStationId1(long stationId1) {
            this.stationId1 = stationId1;
        }

        public long getStationId2() {
            return stationId2;
        }

        public void setStationId2(long stationId2) {
            this.stationId2 = stationId2;
        }

        public LocalDateTime  getStartTime() {
            return startTime;
        }

        public void setStartTime(LocalDateTime  startTime) {
            this.startTime = startTime;
        }

        public LocalDateTime  getEndTime() {
            return endTime;
        }

        public void setEndTime(LocalDateTime  endTime) {
            this.endTime = endTime;
        }

        @Override
        public String toString() {
            return "RequestSearchTrain{" +
                    "stationId1=" + stationId1 +
                    ", stationId2=" + stationId2 +
                    ", startTime=" + startTime +
                    ", endTime=" + endTime +
                    '}';
        }
    }

}