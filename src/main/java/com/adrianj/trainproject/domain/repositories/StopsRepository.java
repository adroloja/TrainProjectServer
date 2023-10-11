package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Stops;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StopsRepository extends CrudRepository<com.adrianj.trainproject.domain.entities.Stops, Long> {

    @Query("select u from Stops u where u.trainStops.id = :train_id")
    Optional<List<Stops>> getTrainStopById(@Param("train_id") long trainId);

    @Query("select u from Stops u where u.stationStop.id = :station_id")
    Optional<List<Stops>> getOneStationTrainStopById(@Param("station_id") long stationId);

    @Query("SELECT s FROM Stops s " +
            "WHERE s.stationStop.id = :station1_id " +
            "AND s.time >= :startTimeInterval " +
            "AND EXISTS (SELECT s2 FROM Stops s2 " +
            "WHERE s2.stationStop.id = :station2_id " +
            "AND s2.time <= :endTimeInterval " +
            "AND s2.trainStops.id = s.trainStops.id)")
    Optional<List<Stops>> getTrainsPassingBetweenStations(@Param("station1_id") long stationId1,
                                                          @Param("station2_id") long stationId2,
                                                          @Param("startTimeInterval")Date startTime,
                                                          @Param("endTimeInterval") Date endTime);

}
