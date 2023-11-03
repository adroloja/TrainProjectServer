package com.adrianj.trainproject.domain;

import com.adrianj.trainproject.domain.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class DataInitializer {

    private final JdbcTemplate jdbcTemplate;
    private PassengerRepository passengerRepository;
    private BCryptPasswordEncoder encoder;
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);


    @Autowired
    public DataInitializer(JdbcTemplate jdbcTemplate, PassengerRepository passengerRepository, BCryptPasswordEncoder encoder) {

        this.passengerRepository = passengerRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.encoder = encoder;
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

        String pass1234 = encoder.encode("1234");

        String sqlPassenger = "INSERT INTO passenger (name, surname, date_birth, username, password, employe) VALUES " +
                "('Adri', 'Jaimez', '1990-01-01', 'adroyoyo', '" + pass1234 + "', true), " +
                "('Adela', 'Jaimez', '2023-01-01', 'adelaj','" + pass1234 + "', false), " +
                "('Maria', 'Díaz', '1990-01-01', 'mariad','" + pass1234 + "', false), " +
                "('Juan', 'Gomez', '1985-05-12', 'juang','" + pass1234 + "', true), " +
                "('Laura', 'Perez', '1988-08-20', 'laurap','" + pass1234 + "', false), " +
                "('Carlos', 'Lopez', '1995-04-03', 'carlosl','" + pass1234 + "', true), " +
                "('Sofia', 'Rodriguez', '1992-11-15', 'sofiar','" + pass1234 + "', false), " +
                "('Andres', 'Martinez', '1987-03-28', 'andresm','" + pass1234 + "', true), " +
                "('Elena', 'Sanchez', '1982-09-10', 'elenas','" + pass1234 + "', false), " +
                "('Javier', 'Fernandez', '1980-06-07', 'javierf','" + pass1234 + "', true), " +
                "('Paula', 'Garcia', '1998-02-25', 'paulag','" + pass1234 + "', false), " +
                "('Antonio', 'Ramirez', '1975-10-18', 'antonior','" + pass1234 + "', true), " +
                "('Ana', 'Torres', '1999-07-29', 'anat','" + pass1234 + "', false), " +
                "('Pedro', 'Sanz', '1984-12-05', 'pedros','" + pass1234 + "', true), " +
                "('Luisa', 'Lopez', '1996-06-17', 'luisal','" + pass1234 + "', false), " +
                "('Raul', 'Gutierrez', '1979-04-30', 'raulg','" + pass1234 + "', true), " +
                "('Carmen', 'Dominguez', '1991-03-14', 'carmed','" + pass1234 + "', false), " +
                "('Pablo', 'Navarro', '1983-08-22', 'pablon','" + pass1234 + "', true), " +
                "('Natalia', 'Ruiz', '1989-11-26', 'nataliar','" + pass1234 + "', false), " +
                "('Hector', 'Jimenez', '1978-07-08', 'hectorj','" + pass1234 + "', true), " +
                "('Marta', 'Ortega', '1994-09-02', 'martaor','" + pass1234 + "', false), " +
                "('Daniel', 'Silva', '1981-05-19', 'daniels','" + pass1234 + "', true), " +
                "('Eva', 'Moreno', '1986-01-03', 'evam','" + pass1234 + "', false), " +
                "('Roberto', 'Castro', '1993-12-12', 'robertoc','" + pass1234 + "', true), " +
                "('Isabel', 'Gomez', '1977-06-23', 'isabelg','" + pass1234 + "', false), " +
                "('Victor', 'Hernandez', '1997-10-07', 'victorh','" + pass1234 + "', true), " +
                "('Cristina', 'Molina', '1980-04-16', 'cristinam','" + pass1234 + "', false), " +
                "('Ruben', 'Serrano', '1992-02-14', 'rubens','" + pass1234 + "', true), " +
                "('Silvia', 'Rojas', '1976-12-28', 'silviar','" + pass1234 + "', false), " +
                "('Miguel', 'Vargas', '1985-08-09', 'miguelv','" + pass1234 + "', true)";

        jdbcTemplate.execute(sqlPassenger);

        String insertStationsSql = "INSERT INTO station (name) VALUES " +
                "('Málaga'), " +
                "('Granada'), " +
                "('Sevilla'), " +
                "('Madrid'), " +
                "('Barcelona'), " +
                "('Valencia'), " +
                "('Bilbao'), " +
                "('Alicante'), " +
                "('Córdoba'), " +
                "('Murcia'), " +
                "('Valladolid'), " +
                "('Zaragoza'), " +
                "('Santander'), " +
                "('Toledo'), " +
                "('Gijón'), " +
                "('Tarragona'), " +
                "('Cádiz'), " +
                "('Huelva'), " +
                "('Pamplona'), " +
                "('Salamanca'), " +
                "('Almería'), " +
                "('Girona'), " +
                "('Lleida'), " +
                "('León'), " +
                "('Oviedo'), " +
                "('Castellón'), " +
                "('Badajoz'), " +
                "('Segovia'), " +
                "('Cuenca'), " +
                "('Ávila')";


        jdbcTemplate.execute(insertStationsSql);

        String insertTrainsSql = "INSERT INTO train (number, seats) VALUES " +
                "(1, 100), " +
                "(2, 120), " +
                "(3, 100), " +
                "(4, 110), " +
                "(5, 90), " +
                "(6, 115), " +
                "(7, 105), " +
                "(8, 95), " +
                "(9, 125), " +
                "(10, 105), " +
                "(11, 100), " +
                "(12, 95), " +
                "(13, 110), " +
                "(14, 120), " +
                "(15, 100), " +
                "(16, 115), " +
                "(17, 90), " +
                "(18, 105), " +
                "(19, 95), " +
                "(20, 125), " +
                "(21, 105), " +
                "(22, 100), " +
                "(23, 95), " +
                "(24, 110), " +
                "(25, 120), " +
                "(26, 100), " +
                "(27, 115), " +
                "(28, 90), " +
                "(29, 105), " +
                "(30, 95)";


        jdbcTemplate.execute(insertTrainsSql);

        String insertScheduleSql = "INSERT INTO schedule (train_id) VALUES " +
                "(1), " +
                "(2), " +
                "(3), " +
                "(4), " +
                "(5), " +
                "(6), " +
                "(7), " +
                "(8), " +
                "(9), " +
                "(10), " +
                "(11), " +
                "(12), " +
                "(13), " +
                "(14), " +
                "(15), " +
                "(16), " +
                "(17), " +
                "(18), " +
                "(19), " +
                "(20), " +
                "(21), " +
                "(22), " +
                "(23), " +
                "(24), " +
                "(25), " +
                "(26), " +
                "(27), " +
                "(28), " +
                "(29), " +
                "(30)";


        jdbcTemplate.execute(insertScheduleSql);

        LocalDateTime fechaInicial = LocalDateTime.of(2023, 11, 10, 10, 30);
        Random random = new Random();

        for (int trainId = 1; trainId <= 30; trainId++) {
            for (int scheduleId = 1; scheduleId <= 30; scheduleId++) {
                String insertStopsesSql = "INSERT INTO stops (station_id, train_id, schedule_id, time) VALUES ";

                for (int i = 0; i < 30; i++) {
                    LocalDateTime horaEstacion1 = fechaInicial.plusDays(i).plusMinutes(0);
                    LocalDateTime horaEstacion2 = fechaInicial.plusDays(i).plusMinutes(30);
                    LocalDateTime horaEstacion3 = fechaInicial.plusDays(i).plusMinutes(60);

                    for (int j = 0; j < 5; j++) {
                        int estacionAleatoria;
                        do {
                            estacionAleatoria = random.nextInt(30) + 1;
                        } while (estacionAleatoria == 1 || estacionAleatoria == 2 || estacionAleatoria == 3);

                        LocalDateTime horaEstacionAleatoria = fechaInicial.plusDays(i).plusMinutes(90 + (j * 30));

                        insertStopsesSql += "(" + estacionAleatoria + ", " + trainId + ", " + scheduleId + ", '" + horaEstacionAleatoria + "'), ";
                    }
                    insertStopsesSql += "(" + 1 + ", " + trainId + ", " + scheduleId + ", '" + horaEstacion1 + "'), " +
                            "(" + 2 + ", " + trainId + ", " + scheduleId + ", '" + horaEstacion2 + "'), " +
                            "(" + 3 + ", " + trainId + ", " + scheduleId + ", '" + horaEstacion3 + "'), ";
                }

                insertStopsesSql = insertStopsesSql.substring(0, insertStopsesSql.length() - 2);

                jdbcTemplate.execute(insertStopsesSql);
            }
        }


        String insertTicket = "INSERT INTO ticket (start_stop_station_id, end_stop_station_id, passenger_id, seat)" +
                "VALUES " +
                "  (1, 3, 2, 1)," +
                "  (2, 3, 3, 2)," +
                "  (1, 2, 1, 3);";

        jdbcTemplate.execute(insertTicket);


    }
}
