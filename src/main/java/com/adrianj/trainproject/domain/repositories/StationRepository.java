package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Station;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StationRepository extends CrudRepository<Station, Long> {

    Optional<Station> findByName(String name);
}
