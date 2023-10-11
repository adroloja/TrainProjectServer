package com.adrianj.trainproject.domain.services;

import com.adrianj.trainproject.domain.entities.Ticket;
import com.adrianj.trainproject.domain.entities.Train;
import com.adrianj.trainproject.domain.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public List<Ticket> getTicketByDay(int trainNumber, String date){

        List<Ticket> tickets = ticketRepository.getTicketByDay(date).get();
        List<Ticket> resultList = new ArrayList<>();

        for(Ticket t : tickets){

            if(t.getStartStops().getTrainStops().getNumber() == trainNumber){

                resultList.add(t);
            }
        }

        return resultList;
    }
}
