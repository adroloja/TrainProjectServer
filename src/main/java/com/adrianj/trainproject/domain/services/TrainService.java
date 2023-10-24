package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TrainService {

    private final TrainRepository trainRepository;
    private final StopsRepository stopsRepository;

    public ResponseEntity<?> createTrain(Train train){

        Optional<Train> optionalTrain = trainRepository.findByNumber(train.getNumber());

        if(optionalTrain.isEmpty()){

            trainRepository.save(train);

            return ResponseEntity.ok(train);
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"The train number is already exist. Please try another train number\"}");
        }
    }

    public ResponseEntity<?> updateTrain(Train train){

        Optional<Train> optionalTrain = trainRepository.findById(train.getId());

        if(optionalTrain.isPresent()){

            Train t = optionalTrain.get();
            t.setNumber(train.getNumber());
            t.setSeats(train.getSeats());

            trainRepository.save(t);

            return ResponseEntity.ok("{\"message\": \"Update completed\"}");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"The train doesn´t exist.\"}");

        }
    }

    public ResponseEntity<?> deleteTrain(long id){

        Optional<Train> optionalTrain = trainRepository.findById(id);

        if(optionalTrain.isPresent()){

            trainRepository.delete(optionalTrain.get());

            return ResponseEntity.ok("{\"message\": \"Delete completed\"}");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"The train doesn´t exist.\"}");
        }
    }

    public ResponseEntity<?> getTrain(long id){

        Optional<Train> optionalTrain = trainRepository.findById(id);

        if(optionalTrain.isPresent()){

            return ResponseEntity.ok(optionalTrain.get());
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"The train doesn´t exist.\"}");
        }
    }

    public ResponseEntity<?> searchTrain(RequestSearchTrain requestSearchTrain){

        LocalDateTime startTime = requestSearchTrain.startTime.plus(2, ChronoUnit.HOURS);
        LocalDateTime endTime = requestSearchTrain.endTime.plus(2, ChronoUnit.HOURS);

        Date startDate = Date.from(startTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endTime.atZone(ZoneId.systemDefault()).toInstant());

        //System.out.println(requestSearchTrain.toString());
        Optional<List<Stops>> optionalStopsList = stopsRepository.getTrainsPassingBetweenStations(requestSearchTrain.getStationId1(), requestSearchTrain.getStationId2(),
                startDate, endDate);

        if(optionalStopsList.isPresent()){

            List<Stops> stopsList = optionalStopsList.get();

            List<Stops> result = new ArrayList<>();
            List<Ticket> tickets = new ArrayList<>();

            for(Stops s : stopsList){

                int dateStart;

                if(s.getStationStop().getId() == requestSearchTrain.getStationId1()){

                    long trainId = s.getTrainStops().getId();

                    dateStart = s.getTime().getDay();

                    stopsList.forEach(n -> {

                        if(n.getTime().getDay() == dateStart && n.getStationStop().getId() == requestSearchTrain.getStationId2() && n.getTrainStops().getId() == trainId){

                            if(!result.contains(n)){


                                result.add(s);
                                result.add(n);

                                Ticket t = new Ticket();
                                t.setEndStops(n);
                                t.setStartStops(s);

                                tickets.add(t);

                            }
                        }
                    });
                }
            }

            return ResponseEntity.ok(tickets);
        }

        return ResponseEntity.ok("ok");

    }

    public ResponseEntity<?> getStationStops(TrainRequest trainRequest){

        return ResponseEntity.ok(this.stopsRepository.getStopsStationByName(trainRequest.getName()));
    }

    public ResponseEntity<?> getStationStopsByDay(TrainRequest trainRequest) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat fullsimpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date date = simpleDateFormat.parse(trainRequest.getDay());

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        Date date1 = simpleDateFormat.parse(trainRequest.getDay());

        date1.setHours(23);
        date1.setMinutes(59);
        date1.setSeconds(59);

        return ResponseEntity.ok(this.stopsRepository.getListStopStationWithTime(trainRequest.getName(), date, date1).get());
    }

    public static class TrainRequest{

        private String name;
        private String day;

        public TrainRequest(String station) {
            this.name = station;

        }

        public TrainRequest() {
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getName() {
            return name;
        }

        public void setName(String station) {
            this.name = station;
        }

    }

    public static class RequestSearchTrain{

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
