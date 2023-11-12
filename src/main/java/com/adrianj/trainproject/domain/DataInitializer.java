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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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

        String sqlPassenger = "INSERT INTO passenger (name, surname, date_birth, username, password, employe, email, validate) VALUES " +
                "('Adri', 'Jaimez', '1990-01-01', 'adroyoyo', '" + pass1234 + "', true, 'adroloja@gmail.com', true), " +
                "('Adela', 'Jaimez', '2023-01-01', 'adelaj','" + pass1234 + "', false, 'adroloja@gmail.com', true), " +
                "('Maria', 'Díaz', '1990-01-01', 'mariad','" + pass1234 + "', false, 'mariadiaz@example.com', true), " +
                "('Juan', 'Gomez', '1985-05-12', 'juang','" + pass1234 + "', true, 'juangomez@example.com', true), " +
                "('Laura', 'Perez', '1988-08-20', 'laurap','" + pass1234 + "', false, 'lauraperez@example.com', true), " +
                "('Carlos', 'Lopez', '1995-04-03', 'carlosl','" + pass1234 + "', true, 'carloslopez@example.com', true), " +
                "('Sofia', 'Rodriguez', '1992-11-15', 'sofiar','" + pass1234 + "', false, 'sofia@example.com', true), " +
                "('Andres', 'Martinez', '1987-03-28', 'andresm','" + pass1234 + "', true, 'andres@example.com', true), " +
                "('Elena', 'Sanchez', '1982-09-10', 'elenas','" + pass1234 + "', false, 'elena@example.com', true), " +
                "('Javier', 'Fernandez', '1980-06-07', 'javierf','" + pass1234 + "', true, 'javier@example.com', true), " +
                "('Paula', 'Garcia', '1998-02-25', 'paulag','" + pass1234 + "', false, 'paula@example.com', true), " +
                "('Antonio', 'Ramirez', '1975-10-18', 'antonior','" + pass1234 + "', true, 'antonio@example.com', true), " +
                "('Ana', 'Torres', '1999-07-29', 'anat','" + pass1234 + "', false, 'ana@example.com', true), " +
                "('Pedro', 'Sanz', '1984-12-05', 'pedros','" + pass1234 + "', true, 'pedro@example.com', true), " +
                "('Luisa', 'Lopez', '1996-06-17', 'luisal','" + pass1234 + "', false, 'luisa@example.com', true), " +
                "('Raul', 'Gutierrez', '1979-04-30', 'raulg','" + pass1234 + "', true, 'raul@example.com', true), " +
                "('Carmen', 'Dominguez', '1991-03-14', 'carmed','" + pass1234 + "', false, 'carmen@example.com', true), " +
                "('Pablo', 'Navarro', '1983-08-22', 'pablon','" + pass1234 + "', true, 'pablo@example.com', true), " +
                "('Natalia', 'Ruiz', '1989-11-26', 'nataliar','" + pass1234 + "', false, 'natalia@example.com', true), " +
                "('Hector', 'Jimenez', '1978-07-08', 'hectorj','" + pass1234 + "', true, 'hector@example.com', true), " +
                "('Marta', 'Ortega', '1994-09-02', 'martaor','" + pass1234 + "', false, 'marta@example.com', true), " +
                "('Daniel', 'Silva', '1981-05-19', 'daniels','" + pass1234 + "', true, 'daniel@example.com', true), " +
                "('Eva', 'Moreno', '1986-01-03', 'evam','" + pass1234 + "', false, 'eva@example.com', true), " +
                "('Roberto', 'Castro', '1993-12-12', 'robertoc','" + pass1234 + "', true, 'roberto@example.com', true), " +
                "('Isabel', 'Gomez', '1977-06-23', 'isabelg','" + pass1234 + "', false, 'isabel@example.com', true), " +
                "('Victor', 'Hernandez', '1997-10-07', 'victorh','" + pass1234 + "', true, 'victor@example.com', true), " +
                "('Cristina', 'Molina', '1980-04-16', 'cristinam','" + pass1234 + "', false, 'cristina@example.com', true), " +
                "('Ruben', 'Serrano', '1992-02-14', 'rubens','" + pass1234 + "', true, 'ruben@example.com', true), " +
                "('Silvia', 'Rojas', '1976-12-28', 'silviar','" + pass1234 + "', false, 'silvia@example.com', true), " +
                "('Miguel', 'Vargas', '1985-08-09', 'miguelv','" + pass1234 + "', true, 'miguel@example.com', true)";


        jdbcTemplate.execute(sqlPassenger);

        String insertStationsSql = "INSERT INTO station (name, lat, lng) VALUES " +
                "('Málaga', '36.7213', '-4.4215'), " +
                "('Granada', '37.1773', '-3.5986'), " +
                "('Sevilla', '37.3886', '-5.9823'), " +
                "('Madrid', '40.4069', '-3.6908'), " +
                "('Barcelona', '41.3797', '2.1408'), " +
                "('Valencia', '39.4664', '-0.3741'), " +
                "('Bilbao', '43.2644', '-2.9345'), " +
                "('Alicante', '38.3440', '-0.4902'), " +
                "('Córdoba', '37.8799', '-4.7817'), " +
                "('Murcia', '37.9793', '-1.1287'), " +
                "('Valladolid', '41.6520', '-4.7286'), " +
                "('Zaragoza', '41.6488', '-0.8891'), " +
                "('Santander', '43.4623', '-3.8099'), " +
                "('Toledo', '39.8628', '-4.0273'), " +
                "('Gijón', '43.5322', '-5.6611'), " +
                "('Tarragona', '41.1189', '1.2445'), " +
                "('Cádiz', '36.5298', '-6.2926'), " +
                "('Huelva', '37.2614', '-6.9447'), " +
                "('Pamplona', '42.8125', '-1.6458'), " +
                "('Salamanca', '40.9701', '-5.6635'), " +
                "('Almería', '36.8340', '-2.4637'), " +
                "('Girona', '41.9794', '2.8214'), " +
                "('Lleida', '41.6176', '0.6223'), " +
                "('León', '42.5987', '-5.5671'), " +
                "('Oviedo', '43.3614', '-5.8593'), " +
                "('Castellón', '39.9864', '-0.0513'), " +
                "('Badajoz', '38.8794', '-6.9707'), " +
                "('Segovia', '40.9429', '-4.1088'), " +
                "('Cuenca', '40.0704', '-2.1374'), " +
                "('Ávila', '40.6566', '-4.7007')";


        /*
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
         */


        jdbcTemplate.execute(insertStationsSql);

        String insertTrainsSql = "INSERT INTO train (number, seats) VALUES " +
                "(1, 2), " +
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

        Date date = new Date();

        //LocalDateTime fechaInicial = LocalDateTime.of(2023, 11, 10, 10, 30);
        int year = date.getYear();
        int month = date.getMonth() + 1;
        LocalDate dateDay = LocalDate.now();
        int dayOfMothn = dateDay.getDayOfMonth();
        date.setMinutes(date.getMinutes() -20);
        int hour = date.getHours();
        int min = date.getMinutes();
        //int hour = 17;
        //int min = 7;
        //System.out.println("year: " + year + " " + "month: " + month + " hour: " + hour + " min: " + min);
        LocalDateTime fechaInicial = LocalDateTime.of(2023, month, dayOfMothn, hour, min);

        Random random = new Random();

        for (int trainId = 1; trainId <= 30; trainId++) {

            String insertStopsesSql = "INSERT INTO stops (station_id, train_id, schedule_id, time) VALUES ";

            for (int i = 0; i < 30; i++) {

                for (int j = 0; j < 5; j++) {
                    int estacionAleatoria;
                    do {
                        estacionAleatoria = random.nextInt(30) + 1;
                    } while (estacionAleatoria == 1 || estacionAleatoria == 2 || estacionAleatoria == 3);

                    LocalDateTime horaEstacionAleatoria = fechaInicial.plusDays(i).plusMinutes(90 + (j * 30));

                    insertStopsesSql += "(" + estacionAleatoria + ", " + trainId + ", " + trainId + ", '" + horaEstacionAleatoria + "'), ";
                }

            }
            // All days this trip all trains
            LocalDateTime horaEstacion1 = fechaInicial.plusDays(trainId).plusMinutes(0);
            LocalDateTime horaEstacion2 = fechaInicial.plusDays(trainId).plusMinutes(30);
            LocalDateTime horaEstacion3 = fechaInicial.plusDays(trainId).plusMinutes(60);
            insertStopsesSql += "(" + 1 + ", " + trainId + ", " + trainId + ", '" + horaEstacion1 + "'), " +
                    "(" + 2 + ", " + trainId + ", " + trainId + ", '" + horaEstacion2 + "'), " +
                    "(" + 3 + ", " + trainId + ", " + trainId + ", '" + horaEstacion3 + "'), ";
            insertStopsesSql = insertStopsesSql.substring(0, insertStopsesSql.length() - 2);

            jdbcTemplate.execute(insertStopsesSql);

        }

        String insertTicket = "INSERT INTO ticket (start_stop_station_id, end_stop_station_id, passenger_id, seat)" +
                "VALUES " +
                "  (1, 3, 2, 1)," +
                "  (1, 2, 1, 2);";

        jdbcTemplate.execute(insertTicket);


    }
}