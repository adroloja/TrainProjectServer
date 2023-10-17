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

    @Query("SELECT u FROM Ticket u WHERE DATE(u.startStops.time) = STR_TO_DATE(:date, '%d/%m/%Y')")
    Optional<List<Ticket>> getTicketByDay(@Param("date") String date);

    @Query("select u from Ticket u where u.startStops.trainStops.number = :trainNumber and u.startStops.time BETWEEN :date and :date1")
    Optional<List<Ticket>>  getListPassenger(@Param("trainNumber") int trainNUmber, @Param("date") Date dateStart, @Param("date1") Date dateEnd);

    @Query("select u from Ticket u where u.passenger.id = :idPassenger and u.startStops.time between :startDate and :endDate")
    Optional<List<Ticket>> getListTicketByIdAndDay(@Param("idPassenger") long idPassenger,
                                                   @Param("startDate") Date startDate,
                                                   @Param("endDate") Date endDate);
}
