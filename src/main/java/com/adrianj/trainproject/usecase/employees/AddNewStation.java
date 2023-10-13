package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.repositories.StationRepository;
import com.adrianj.trainproject.domain.services.StationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AddNewStation {

    private final StationService stationService;
    @PostMapping("/addStation")
    public ResponseEntity<?> addStation(@RequestBody Station station){

      return stationService.createStation(station);
    }
}
