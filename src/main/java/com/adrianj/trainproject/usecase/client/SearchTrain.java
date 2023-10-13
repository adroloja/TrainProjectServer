package com.adrianj.trainproject.usecase.client;


import com.adrianj.trainproject.domain.services.TrainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class SearchTrain {

    private final TrainService trainService;

    @PostMapping("/searchTrain")
    public ResponseEntity<?> search(@RequestBody TrainService.RequestSearchTrain requestSearchTrain) {

        return trainService.searchTrain(requestSearchTrain);
    }


}