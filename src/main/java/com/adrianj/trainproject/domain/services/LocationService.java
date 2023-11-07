package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Location;
import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.exception.ResourceNotFoundException;
import com.adrianj.trainproject.domain.exception.ResponseException;
import com.adrianj.trainproject.domain.repositories.LocationRepository;
import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final PassengerRepository passengerRepository;

    public ResponseEntity<?> getAllLocationByUser(Passenger passenger){

        List<Location> locationOptional = locationRepository.findAllByPassenger(passenger).orElseThrow( () -> new ResourceNotFoundException("There isn´t any location for this passenger"));

        if(locationOptional.isEmpty()){

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("\"message\":\"There isn´t any location for this passenger\"");
        }else{

            return ResponseEntity.ok(locationOptional);
        }
        }

    public ResponseEntity<?> getLocationBetweenDate(LocationByDateDTO location) throws ParseException {

        // check format
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = simpleDateFormat.parse(location.getStartDate());
        Date endDate = simpleDateFormat.parse(location.getEndDate());

        Passenger passenger = passengerRepository.findById(location.getIdPassenger())
                .orElseThrow(() -> new ResourceNotFoundException("Thre isn´t any passenger with this id: " +  location.getIdPassenger()));

        Optional<List<Location>> listOptional = locationRepository.locationByPassengerAndDate(passenger.getId(), startDate, endDate);

        if(listOptional.isPresent()){

            return ResponseEntity.ok(listOptional.get());

        }else{

            return new ResponseException().getNotFoundException(new ResourceNotFoundException("There isn´t any location"),
                    "There isn´t any location for this username: " +  passenger.getUsername());
        }
    }

    public void insertLocation(InsertLocationDTO request){

        if(request.getId() == 0){

            Passenger unkwonPassenger = new Passenger();
            unkwonPassenger.setUsername("Unkown");
            unkwonPassenger.setName("unknown");

            if(passengerRepository.findByUsername("Unkown").isEmpty()){

                passengerRepository.save(unkwonPassenger);
                Location location = new Location();
                location.setPassenger(unkwonPassenger);
                location.setLat(request.getLat());
                location.setLng(request.getLng());

                locationRepository.save(location);

            }else{

                Passenger uk = passengerRepository.findByUsername("Unkown").get();
                Location location = new Location();
                location.setPassenger(uk);
                location.setLat(request.getLat());
                location.setLng(request.getLng());

                locationRepository.save(location);
            }

        }else{

            Passenger passenger = passengerRepository.findById(request.getId()).orElseThrow(() -> new ResourceNotFoundException("There isn´t any passenger with id: " + request.getId()));

            Location location = new Location();
            location.setPassenger(passenger);
            location.setLat(request.getLat());
            location.setLng(request.getLng());

            locationRepository.save(location);
        }
    }

        @RequiredArgsConstructor
        public static class LocationByDateDTO{

        private long idPassenger;
        private String startDate;
        private String endDate;

            public long getIdPassenger() {
                return idPassenger;
            }

            public void setIdPassenger(long idPassenger) {
                this.idPassenger = idPassenger;
            }

            public String getStartDate() {
                return startDate;
            }

            public void setStartDate(String startDate) {
                this.startDate = startDate;
            }

            public String getEndDate() {
                return endDate;
            }

            public void setEndDate(String endDate) {
                this.endDate = endDate;
            }
        }
        public static class InsertLocationDTO{

        private long id;
        private double lat;
        private double lng;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }
}
