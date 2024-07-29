package com.weaterstats.project3.weatherStats.services;

import com.weaterstats.project3.weatherStats.entities.Forecast;
import com.weaterstats.project3.weatherStats.repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ForecastService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ForecastRepository forecastRepository;

    public String getWeather(String city) {
        String url = String.format("https://wttr.in/%s?format=\"%%t +%%h + %%w + %%u + %%C\"", city.replace(" ", "+"));
        try {
            //GET Request
            String response = restTemplate.getForObject(url, String.class);
            return response != null ? response : "";
        } catch (Exception e) {
            return "";
        }
    }
    public void saveSearch(String city, String weatherInfo) {
        Forecast forecast = new Forecast();
        String[] parts = weatherInfo.split(" +");
        if (parts.length >= 5) {
            forecast.setCity(city);
            System.out.println("Temperature: " + parts[0]);
            forecast.setTemp(Integer.parseInt(parts[0].replace("-", "").replace("+", "").replace("°C","").replace("\"","")));
            System.out.println("Humidity: " + parts[1]);
            forecast.setHumidity(parts[1]);
            System.out.println("Windspeed: " + parts[2]);
            forecast.setWindspeed(parts[2]);
            System.out.println("Unv Index: " + parts[3]);
            forecast.setUnv_index(parts[3]);
            System.out.println("Text: " + parts[4] + "\n");
            forecast.setText(parts[4]);
            forecast.setTimestamp(LocalDateTime.now());
            //Save to Database
            forecastRepository.save(forecast);
        } else {
            System.err.println("Unexpected weather data format: " + weatherInfo);
        }
    }

    public void printSearchStatistics() {
        long totalSearches = forecastRepository.count();
        System.out.println("Total number of searches: " + totalSearches);
        Double avgTemp = forecastRepository.findAverageTemperature();
        System.out.println(String.format("Average temperature: %.2f°C", avgTemp));
        List<Object[]> topSearchedCities = forecastRepository.findTopSearchedCities();
        if (!topSearchedCities.isEmpty()) {
            System.out.println("Top 5 most searched cities:");
            for (Object[] cityInfo : topSearchedCities) {
                String cityName = (String) cityInfo[0];
                Long cityCount = (Long) cityInfo[1];
                Double cityAvgTemp = (Double) cityInfo[2];
                System.out.println(String.format("%s: %d searches, Average Temperature: %.2f°C", cityName, cityCount, cityAvgTemp));
            }
        } else {
            System.out.println("No searches found.");
        }
    }

    public void printAverageTemperatureOfCity(String city) {
        Optional<Double> averageTemperature = forecastRepository.findAverageTemperatureByCity(city);
        if (averageTemperature.isPresent()) {
            System.out.println(String.format("The average temperature in %s is: %.2f°C", city, averageTemperature.get()));
        } else {
            System.out.println("No temperature data available for " + city);
        }
    }
}
