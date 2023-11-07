package com.adrianj.trainproject.domain.controller;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.services.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping("/insertLocation")
    public void insertLocation(@RequestBody LocationService.InsertLocationDTO request){

        locationService.insertLocation(request);
    }

    @PostMapping("/getLocationPassenger")
    public ResponseEntity<?> getLocationByPassenger(@RequestBody Passenger passenger){

        return locationService.getAllLocationByUser(passenger);
    }

    @PostMapping("/getLocationPassengerBetweenDate")
    public ResponseEntity<?> getLocationByPassengerAndBetweenDate(@RequestBody LocationService.LocationByDateDTO request) throws ParseException {

        return locationService.getLocationBetweenDate(request);
    }


}
