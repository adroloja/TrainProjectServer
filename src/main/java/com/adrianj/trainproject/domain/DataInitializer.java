package com.adrianj.trainproject.domain;

import com.adrianj.trainproject.domain.entities.*;
import com.adrianj.trainproject.domain.repositories.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);
    private PassengerRepository passengerRepository;
    private ScheduleRepository scheduleRepository;
    private StationRepository stationRepository;
    private StopsRepository stopsRepository;
    private TicketRepository ticketRepository;
    private TrainRepository trainRepository;

    @Autowired
    public DataInitializer(PassengerRepository passengerRepository, ScheduleRepository scheduleRepository,
                           StationRepository stationRepository, StopsRepository stopsRepository, TicketRepository ticketRepository,
                           TrainRepository trainRepository) {

        this.passengerRepository = passengerRepository;
        this.scheduleRepository = scheduleRepository;
        this.stationRepository = stationRepository;
        this.stopsRepository = stopsRepository;
        this.ticketRepository = ticketRepository;
        this.trainRepository = trainRepository;
    }

    @PostConstruct
    public void run() throws IOException {

        logger.info("Starting data storage");
        ObjectMapper objectMapper = new ObjectMapper();
        String dataJson = Files.readString(Path.of("src/main/resources/Mock-data/data.json"));
        MockData mockData = objectMapper.readValue(dataJson,MockData.class);

        if(passengerRepository.count() > 0){

            logger.info("Data is already defined");
            return;
        }

        // Adding passenger
        for(Passenger p : mockData.getPassengers()){

            passengerRepository.save(p);
        }

        // Adding Station

        for(Station s : mockData.getStations()){

            stationRepository.save(s);
        }

        // Adding train

        for(Train t : mockData.getTrains()){

            trainRepository.save(t);
        }

        // Adding schedule

        for(Schedule s : mockData.getSchedules()){

            scheduleRepository.save(s);
        }

        // Adding stops
        for(Stops s : mockData.getStopses()){

            stopsRepository.save(s);
        }

        // Adding ticket
        for(Ticket t : mockData.getTickets()){

            ticketRepository.save(t);
        }






    }

    private static class MockData {

        private List<Passenger> passengers;
        private List<Schedule> schedules;
        private List<Station> stations;
        private List<Stops> stopses;
        private List<Ticket> tickets;
        private List<Train> trains;

        public List<Passenger> getPassengers() {
            return passengers;
        }

        public void setPassengers(List<Passenger> passengers) {
            this.passengers = passengers;
        }

        public List<Schedule> getSchedules() {
            return schedules;
        }

        public void setSchedules(List<Schedule> schedules) {
            this.schedules = schedules;
        }

        public List<Station> getStations() {
            return stations;
        }

        public void setStations(List<Station> stations) {
            this.stations = stations;
        }

        public List<Stops> getStopses() {
            return stopses;
        }

        public void setStopses(List<Stops> stopses) {
            this.stopses = stopses;
        }

        public List<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(List<Ticket> tickets) {
            this.tickets = tickets;
        }

        public List<Train> getTrains() {
            return trains;
        }

        public void setTrains(List<Train> trains) {
            this.trains = trains;
        }
    }
}
