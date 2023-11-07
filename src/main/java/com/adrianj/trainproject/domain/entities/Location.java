package com.adrianj.trainproject.domain.entities;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private double lat;

    private double lng;

    @ManyToOne
    @JoinColumn(name = "passenger_id")
    private Passenger passenger;

    @CreationTimestamp
    private Date createdAt;

}
