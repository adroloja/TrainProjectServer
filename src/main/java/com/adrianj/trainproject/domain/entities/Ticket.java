package com.adrianj.trainproject.domain.entities;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "start_stationTime_id")
    private Stops startStops;

    @ManyToOne
    @JoinColumn(name = "end_stationTime_id")
    private Stops endStops;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;


}



