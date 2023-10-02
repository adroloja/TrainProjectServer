package com.adrianj.trainproject.domain.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn( name = "train_id")
    private Train train;

    @ManyToOne
    @JoinColumn( name = "station_id")
    private Station station;

    @JsonFormat(pattern = "HH:mm:ss", timezone = "UTC")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private Date time;

    @Column(columnDefinition = "integer default 0")
    private String mon;

    @Column(columnDefinition = "integer default 0")
    private String sun;

    @Column(columnDefinition = "integer default 0")
    private String tues;

    @Column(columnDefinition = "integer default 0")
    private String wed;

    @Column(columnDefinition = "integer default 0")
    private String thurs;

    @Column(columnDefinition = "integer default 0")
    private String fri;

    @Column(columnDefinition = "integer default 0")
    private String sat;



}
