package com.adrianj.trainproject.usecase.client;

import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.services.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

class SearchTrainTest {

    private List<Ticket> ticketList;
    private TrainService.RequestSearchTrain requestSearchTrain;
    @BeforeEach
    void setUp() {

        ticketList = new ArrayList<>();
    }

    @Test
    void search() {

        TrainService trainService = Mockito.mock(TrainService.class);
        SearchTrain searchTrain = new SearchTrain(trainService);

        ResponseEntity<?> expectedResponse = ResponseEntity.ok(ticketList);

        Mockito.<ResponseEntity<?>>when(trainService.searchTrain(requestSearchTrain)).thenReturn(expectedResponse);

        ResponseEntity<?> actualResponse = searchTrain.search(requestSearchTrain);

        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }
}