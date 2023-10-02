package com.adrianj.trainproject.domain.repositories;

import com.adrianj.trainproject.domain.entities.Schedule;
import com.adrianj.trainproject.domain.entities.Station;
import com.adrianj.trainproject.domain.entities.Train;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends CrudRepository<Schedule, Long> {

    Optional<List<Schedule>> findAllByStation(Station station);
    Optional<List<Schedule>> findAllByTrain(Train train);

}
