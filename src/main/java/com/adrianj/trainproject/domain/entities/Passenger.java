package com.adrianj.trainproject.domain.entities;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Passenger {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String surname;
    private String dateBirth;

    // Could be usefull these strings;
    private String username;
    private String password;

    private boolean employe;
}