package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
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

    public List<Stops> getStopsTrainFromStartTime(int trainNumber, String startTimeS) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date endTime = simpleDateFormat.parse(startTimeS);

        endTime.setHours(23);
        endTime.setHours(59);

        Date startTime = simpleDateFormat.parse(startTimeS);
        startTime.setHours(startTime.getHours() + 2);
        startTime.setMinutes(startTime.getMinutes() + 1);

        return stopsRepository.getStopsTrainFromStartTime(trainNumber, startTime, endTime).get();
    }

    public ResponseEntity<?> createStop(Stops stops){

        Optional<List<Stops>> optionalStops = Optional.of((List<Stops>) stopsRepository.findAll());

        if(optionalStops.isPresent()){

            AtomicBoolean exist = new AtomicBoolean(false);
            List<Stops> stopsList = optionalStops.get();
            stopsList.forEach(stop -> {
                if(stop.getTrainStops().getNumber() == stops.getTrainStops().getNumber() && stop.getStationStop().getName().equals(stops.getStationStop().getName())
                        && stop.getTime().equals(stops.getTime())){

                    exist.set(true);
                }
            });

            if(exist.get()){

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("The Stops is already exist.");
            }else{

                stopsRepository.save(stops);
                return ResponseEntity.ok(stops);
            }
        }else{

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
