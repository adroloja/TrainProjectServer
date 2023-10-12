package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StopService {

    private final StopsRepository stopsRepository;

    public List<Stops> getStopsTrainFromStartTime(int trainNumber, String startTimeS) throws ParseException {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date endTime = simpleDateFormat.parse(startTimeS);

        endTime.setHours(23);
        endTime.setHours(59);

        Date startTime = simpleDateFormat.parse(startTimeS);
        startTime.setHours(startTime.getHours() + 2);
        startTime.setMinutes(startTime.getMinutes() + 1);

        return stopsRepository.getStopsTrainFromStartTime(trainNumber, startTime, endTime).get();
    }

}
