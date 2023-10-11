package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Station;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StationRepository extends CrudRepository<Station, Long> {

    Optional<Station> findByName(String name);

    @Query("select u from Station u where u.name = :name")
    Optional<Station> getStationByName(@Param("name") String name);

    @Query("select u from Station u")
    Optional<List<Station>> getAllStation();
}
