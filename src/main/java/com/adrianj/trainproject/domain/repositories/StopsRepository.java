package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Stops;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StopsRepository extends CrudRepository<com.adrianj.trainproject.domain.entities.Stops, Long> {

    @Query("select u from Stops u where u.time between :startTime and :endTime")
    Optional<List<Stops>> getStopsAllTrainByDay(@Param("startTime") Date startTime,
                                                @Param("endTime") Date endTime);
    @Query("select u from Stops u where u.stationStop.name = :stationName")
    Optional<List<Stops>> getStopsStationByName(@Param("stationName") String stationName);

    @Query("select u from Stops u where u.stationStop.name = :stationName and u.time between :startDate and :endDate")
    Optional<List<Stops>> getListStopStationWithTime(@Param("stationName") String stationName,
                                                     @Param("startDate") Date startDate,
                                                     @Param("endDate") Date endDate);

    @Query("SELECT u FROM Stops u WHERE (u.stationStop.id = :stationA OR u.stationStop.id = :stationB) AND u.time >= :startTime AND u.time <= :endTime")
    Optional<List<Stops>> getTrainsPassingBetweenStations(
            @Param("stationA") long stationId1,
            @Param("stationB") long stationId2,
            @Param("startTime") Date startTime,
            @Param("endTime") Date endTime
    );

    @Query("SELECT u FROM Stops u WHERE u.trainStops.number = :numberTrain AND u.time BETWEEN :startTime AND :endTime")
    Optional<List<Stops>> getStopsTrainFromStartTime(@Param("numberTrain") int trainNumber,
                                                     @Param("startTime") Date startTime,
                                                     @Param("endTime") Date endTime);

}
