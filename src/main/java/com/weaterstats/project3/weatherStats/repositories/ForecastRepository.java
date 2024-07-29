package com.weaterstats.project3.weatherStats.repositories;

import com.weaterstats.project3.weatherStats.entities.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ForecastRepository extends JpaRepository<Forecast, Integer> {

    @Query("SELECT f.city, COUNT(f.id), AVG(f.temp) FROM Forecast f GROUP BY f.city ORDER BY COUNT(f.id) DESC, AVG(f.temp) DESC")
    List<Object[]> findTopSearchedCities();

    @Query("SELECT AVG(f.temp) FROM Forecast f")
    Double findAverageTemperature();

    @Query("SELECT AVG(f.temp) FROM Forecast f WHERE f.city = :cityName")
    Optional<Double> findAverageTemperatureByCity(@Param("cityName") String cityName);
}
