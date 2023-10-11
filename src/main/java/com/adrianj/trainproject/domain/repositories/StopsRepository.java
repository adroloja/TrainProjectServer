package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Stops;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface StopsRepository extends CrudRepository<com.adrianj.trainproject.domain.entities.Stops, Long> {

    @Query("select u from Stops u where u.stationStop.id = :station_id")
    Optional<List<Stops>> getOneStationTrainStopById(@Param("station_id") long stationId);

    @Query("select u from Stops u where u.stationStop.id = :stationA or u.stationStop.id = :stationB and u.time < :startTime and u.time > :endTime")
    Optional<List<Stops>> getTrainsPassingBetweenStations(@Param("stationA") long stationId1,
                                                @Param("stationB") long stationId2,
                                                @Param("startTime") Date startTime,
                                                @Param("endTime") Date endTime);

}
