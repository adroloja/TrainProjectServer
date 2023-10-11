package com.adrianj.trainproject.domain.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Stops {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station stationStop;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train trainStops;


    @ManyToOne
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "UTC")
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date time;


}
