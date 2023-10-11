package com.adrianj.trainproject.domain.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int number;
    private int seats;

/*
    @JsonIgnore
    @OneToMany(mappedBy = "trainStops")
    private List<Stops> links;

    @JsonIgnore
    @OneToMany(mappedBy = "train")
    private List<Schedule> scheduleList;
 */

}
