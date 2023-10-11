package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Stops;
import com.adrianj.trainproject.domain.repositories.StopsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StopService {

    private final StopsRepository stopsRepository;

}
