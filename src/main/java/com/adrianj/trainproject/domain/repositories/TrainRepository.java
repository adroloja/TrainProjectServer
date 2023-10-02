package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Train;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepository extends CrudRepository<Train, Long> {

    // If we want to search by Number
    Optional<Train> findByNumber(int number);
}
