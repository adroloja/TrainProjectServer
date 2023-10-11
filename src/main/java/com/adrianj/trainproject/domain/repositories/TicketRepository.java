package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.entities.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Optional<Ticket> findByPassenger(Passenger passenger);

    @Query("select u from Ticket u where u.startStops.trainStops.number = :trainNumber and DATE_FORMAT(u.startStops.time, '%d/%m/%Y') = :date ")    // check that, do with chatgpt time filter.
    Optional<List<Ticket>>  getListPassenger(@Param("trainNumber") int trainNUmber, @Param("date") String date);
}
