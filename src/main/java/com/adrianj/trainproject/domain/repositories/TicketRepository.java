package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Passenger;
import com.adrianj.trainproject.domain.entities.Ticket;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {

    Optional<Ticket> findByPassenger(Passenger passenger);

    @Query("select u from Ticket u where u.train.number = ?1 and u.day = ?2")
    Optional<List<Ticket>>  getListPassenger(String trainNUmber, String date);
}
