package com.weaterstats.project3.weatherStats.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Customer {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    int id;
    String username;
    String password;
    String mail;
    @OneToMany
    List<Forecast> forecasts = new ArrayList<>();
}
