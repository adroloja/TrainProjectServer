package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Schedule;
import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.ScheduleRepository;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class StopService {

    private final StopsRepository stopsRepository;
    private final StationRepository stationRepository;
    private final ScheduleRepository scheduleRepository;
    private final TrainRepository trainRepository;


    public ResponseEntity<?> getAllStops(){

        return ResponseEntity.ok((List<Stops>) stopsRepository.findAll());
    }

    public List<Stops> getStopsTrainFromStartTime(int trainNumber, String startTimeS) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date endTime = simpleDateFormat.parse(startTimeS);

        endTime.setHours(23);
        endTime.setMinutes(59);

        Date startTime = simpleDateFormat.parse(startTimeS);
        startTime.setHours(startTime.getHours() + 2);
        startTime.setMinutes(startTime.getMinutes() + 1);

        return stopsRepository.getStopsTrainFromStartTime(trainNumber, startTime, endTime).get();
    }

    public List<Stops> getStopsTrainFromDay(int trainNumber, String startTimeS) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        Date endTime = simpleDateFormat.parse(startTimeS);
        Date startTime = new Date(endTime.getTime());

        endTime.setHours(23);
        endTime.setMinutes(59);
        endTime.setSeconds(59);

        startTime.setHours(0);
        startTime.setMinutes(0);
        startTime.setSeconds(0);

        return stopsRepository.getStopsTrainFromStartTime(trainNumber, startTime, endTime).get();
    }

    public ResponseEntity<?> createStop(RequestCreateStop requestCreateStop) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        // Get objects

        Station station = stationRepository.findById((long) requestCreateStop.getStationId()).get();
        Train train = trainRepository.findById((long) requestCreateStop.getTrainId()).get();
        Schedule schedule = scheduleRepository.findById((long) requestCreateStop.getScheduleId()).get();
        Date date = simpleDateFormat.parse(requestCreateStop.getTime());

        Optional<List<Stops>> optionalStops = Optional.of((List<Stops>) stopsRepository.findAll());

            AtomicBoolean exist = new AtomicBoolean(false);
            List<Stops> stopsList = optionalStops.get();
            stopsList.forEach(stop -> {
                if(stop.getTrainStops().getNumber() == train.getNumber() && stop.getStationStop().getName().equals(station.getName())
                        && stop.getTime().equals(date)){

                    exist.set(true);
                }
            });

            if(exist.get()){

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The Stops is already exist.");
            }else{

                Stops stops = new Stops();
                stops.setStationStop(station);
                stops.setSchedule(schedule);
                stops.setTrainStops(train);
                stops.setTime(date);

                stopsRepository.save(stops);
                return ResponseEntity.ok(stops);
            }

    }

    public ResponseEntity<?> updateStops(Stops stops){

        Optional<Stops> optionalStops = stopsRepository.findById(stops.getId());

        if(optionalStops.isPresent()){

            stopsRepository.save(stops);

            return ResponseEntity.ok("Update completed");
        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The stops doesn´t exist.");
        }
    }

    public ResponseEntity<?> deleteStops(long id){

        Optional<Stops> optionalStops = stopsRepository.findById(id);

        if(optionalStops.isPresent()){

            stopsRepository.delete(optionalStops.get());

            return ResponseEntity.ok("Delete completed");

        }else{

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The stops doesn´t exist.");

        }
    }

    public static class RequestCreateStop{

        private int trainId;
        private int scheduleId;
        private int stationId;
        private String time;

        public int getTrainId() {
            return trainId;
        }

        public void setTrainId(int trainId) {
            this.trainId = trainId;
        }

        public int getScheduleId() {
            return scheduleId;
        }

        public void setScheduleId(int scheduleId) {
            this.scheduleId = scheduleId;
        }

        public int getStationId() {
            return stationId;
        }

        public void setStationId(int stationId) {
            this.stationId = stationId;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class ResquestgetStopsTrainFromStartTime {

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
