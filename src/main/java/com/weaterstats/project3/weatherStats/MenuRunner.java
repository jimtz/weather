package com.weaterstats.project3.weatherStats;

import com.weaterstats.project3.weatherStats.services.ForecastService;
import com.weaterstats.project3.weatherStats.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class MenuRunner implements CommandLineRunner {

    @Autowired
    UserService userService;

    @Autowired
    ForecastService forecastService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome to the Weather App!");
            System.out.println("1. Login\n2. Register\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    if (userService.login(scanner)) {
                        performWeatherOperations();
                    }
                    break;
                case 2:
                    if (userService.register(scanner)) {
                        performWeatherOperations();
                    }
                    break;
                case 3:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void performWeatherOperations() throws Exception {
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("Select an option:");
            System.out.println("1. Get weather for a city");
            System.out.println("2. View search statistics");
            System.out.println("3. Get Average Temperature of city based on user searches");
            System.out.println("4. Logout");
            Scanner scanner = new Scanner(System.in);
            Scanner scanner1 = new Scanner(System.in);
            String city;
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Enter city name:");
                    city = scanner1.nextLine();
                    String weatherInfo = forecastService.getWeather(city);
                    if(!weatherInfo.isEmpty()) {
                        forecastService.saveSearch(city, weatherInfo);
                    } else {
                        System.out.println("Not valid city name");
                    }
                    break;
                case 2:
                    forecastService.printSearchStatistics();
                    break;
                case 3:
                    System.out.println("Enter city name:");
                    city = scanner1.nextLine();
                    forecastService.printAverageTemperatureOfCity(city);
                    break;
                case 4:
                    loggedIn = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
