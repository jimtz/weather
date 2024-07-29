package com.weaterstats.project3.weatherStats.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String city;
    private LocalDateTime timestamp;
    private int temp;
    private String humidity;
    private String windspeed;
    private String unv_index;
    private String text;
}
