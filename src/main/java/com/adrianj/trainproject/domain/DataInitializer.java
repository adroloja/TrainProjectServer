package com.adrianj.trainproject.domain;

import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Component
public class DataInitializer {

    private final JdbcTemplate jdbcTemplate;
    private PassengerRepository passengerRepository;
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);


    @Autowired
    public DataInitializer(JdbcTemplate jdbcTemplate, PassengerRepository passengerRepository) {

        this.passengerRepository = passengerRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    @PostConstruct()
    public void initializeData() {

        /*

        // Generator secret key
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String secret = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("jwt.secret=" + secret);
         */

        if(passengerRepository.count() > 0){

            logger.info("Data is already defined");
            return;
        }

        logger.info("Data initializer ON");

        String sqlPassenger = "INSERT INTO passenger (name, surname, date_birth, username, password, employe) VALUES " +
                "('Adri', 'Jaimez', '1990-01-01', 'adroyoyo', '1234', true), " +
                "('Adela', 'Jaimez', '2023-01-01', 'adelaj', '1234', false), " +
                "('Maria', 'Díaz', '1990-01-01', 'mariad', '1234', false)";

        jdbcTemplate.execute(sqlPassenger);

        String insertStationsSql = "INSERT INTO station (name) VALUES " +
                "('Málaga'), " +
                "('Granada'), " +
                "('Sevilla')";

        jdbcTemplate.execute(insertStationsSql);

        String insertTrainsSql = "INSERT INTO train (number, seats) VALUES " +
                "(1, 100), " +
                "(2, 120), " +
                "(3, 130)";

        jdbcTemplate.execute(insertTrainsSql);

        String insertScheduleSql = "INSERT INTO schedule (train_id)" +
                "VALUES" +
                "    (1);";

        jdbcTemplate.execute(insertScheduleSql);

        String insertStopsesSql = "INSERT INTO stops (station_id, train_id, schedule_id, time) VALUES " +
                "(1, 1, 1, '2023-10-08 10:30:00'), " +
                "(2, 1, 1, '2023-10-08 11:30:00'), " +
                "(3, 1, 1, '2023-10-08 12:30:00')";

        jdbcTemplate.execute(insertStopsesSql);

        String insertTicket = "INSERT INTO ticket (start_stop_station_id, end_stop_station_id, passenger_id, seat)" +
                "VALUES " +
                "  (1, 3, 2, 1)," +
                "  (2, 3, 3, 2)," +
                "  (1, 2, 1, 3);";

        jdbcTemplate.execute(insertTicket);


    }
}
