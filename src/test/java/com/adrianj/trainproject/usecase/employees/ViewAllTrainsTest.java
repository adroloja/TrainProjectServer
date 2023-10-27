package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.TrainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewAllTrainsTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void getAllTrain() {

        TrainRepository trainRepository = Mockito.mock(TrainRepository.class);
        ViewAllTrains viewAllTrains = new ViewAllTrains(trainRepository);

        List<Train> trainList = new ArrayList<>();
        Train t = new Train();
        t.setNumber(16);
        trainList.add(t);

        List<Train> expectedResponseRepository = trainList;

        Mockito.when((List<Train>)trainRepository.findAll()).thenReturn(expectedResponseRepository);

        // Check return status
        ResponseEntity<?> response = viewAllTrains.getAllTrain();
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Check content return
        ResponseEntity<?> expectedResponse = ResponseEntity.ok(trainList);
        assertEquals(response, expectedResponse);

    }
}