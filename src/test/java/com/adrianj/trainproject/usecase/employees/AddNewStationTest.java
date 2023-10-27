package com.adrianj.trainproject.usecase.employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.services.StationService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AddNewStationTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void addStation() {

        StationService stationService = Mockito.mock(StationService.class);
        AddNewStation addNewStation = new AddNewStation(stationService);

        Station station = new Station();
        station.setName("Test Station");

        ResponseEntity<?> expectedResponse = ResponseEntity.ok("Station created successfully");

        Mockito.<ResponseEntity<?>>when(stationService.createStation(station)).thenReturn(expectedResponse);

        ResponseEntity<?> actualResponse = addNewStation.addStation(station);

        // Assert
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        assertEquals(expectedResponse.getBody(), actualResponse.getBody());
    }
}