package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Passenger;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends CrudRepository<Passenger, Long> {

    Optional<Passenger> findByName(String name);
    Optional<Passenger> findByUsername(String username);
}
