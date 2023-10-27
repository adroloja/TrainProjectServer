package com.adrianj.trainproject.usecase.employees;

import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.services.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AddNewTrainTest {
    private Train train;
    @BeforeEach
    void setUp() {


        train = new Train();
        train.setNumber(100);
        train.setSeats(120);
    }

    @Test
    void addTrain() {

        TrainService trainService = Mockito.mock(TrainService.class);
        AddNewTrain addNewTrain = new AddNewTrain(trainService);

        ResponseEntity<?> response = ResponseEntity.ok("Train created successfully");

        Mockito.<ResponseEntity<?>>when(trainService.createTrain(train)).thenReturn(response);

        ResponseEntity<?> actualResponse = addNewTrain.addTrain(train);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(response.getBody(), actualResponse.getBody());
    }
}