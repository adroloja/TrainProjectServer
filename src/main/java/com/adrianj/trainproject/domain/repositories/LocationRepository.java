package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Location;
import com.adrianj.trainproject.domain.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {

    Optional<List<Location>> findAllByPassenger(Passenger passenger);

    @Query("select u from Location u where u.passenger.id = :idPassenger and u.createdAt between :startTime and :endTime")
    Optional<List<Location>> locationByPassengerAndDate(@Param("idPassenger") long id,
                                                        @Param("startTime") Date starTime,
                                                        @Param("endTime") Date endTime);

}
