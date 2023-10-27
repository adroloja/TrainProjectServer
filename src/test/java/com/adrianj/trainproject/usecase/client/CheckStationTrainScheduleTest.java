package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.services.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CheckStationTrainScheduleTest {

    private TrainService.TrainRequest trainRequest;
    private CheckStationTrainSchedule checkStationTrainSchedule;
    private List<Stops> stopsList;
    private TrainService trainService;
    @BeforeEach
    void setUp() {

        trainService = Mockito.mock(TrainService.class);
        checkStationTrainSchedule = new CheckStationTrainSchedule(trainService);

        trainRequest = new TrainService.TrainRequest();
        trainRequest.setDay("2023/10/08");
        trainRequest.setName("Málaga");

        stopsList = new ArrayList<>();
    }

    @Test
    void getTrainSchedule() {

        ResponseEntity<?> expectedResponse = ResponseEntity.ok(stopsList);

        Mockito.<ResponseEntity<?>>when(trainService.getStationStops(trainRequest)).thenReturn(expectedResponse);

        ResponseEntity<?> actualResponse = checkStationTrainSchedule.getTrainSchedule(trainRequest);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }

    @Test
    void getTrainScheduleByDay() throws ParseException {

        ResponseEntity<?> expectedResponse = ResponseEntity.ok(stopsList);

        Mockito.<ResponseEntity<?>>when(trainService.getStationStopsByDay(trainRequest)).thenReturn(expectedResponse);

        ResponseEntity<?> actualResponse = checkStationTrainSchedule.getTrainScheduleByDay(trainRequest);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }
}