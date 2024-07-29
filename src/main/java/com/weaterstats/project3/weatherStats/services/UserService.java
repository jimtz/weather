package com.weaterstats.project3.weatherStats.services;

import com.weaterstats.project3.weatherStats.entities.Customer;
import com.weaterstats.project3.weatherStats.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Scanner;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean login(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        Optional<Customer> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            Customer user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("Login successful.");
                return true;
            } else {
                System.out.println("Login failed. Incorrect password.");
            }
        } else {
            System.out.println("User not found.");
        }
        return false;
    }

    public boolean register(Scanner scanner) {
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        String password = "";
        String confirmPassword = "";
        do {
            System.out.println("Enter password:");
            password = scanner.nextLine();
            System.out.println("Confirm password:");
            confirmPassword = scanner.nextLine();
            if(!confirmPassword.equals(password)){
                System.out.println("Passwords don't match");
            }
        } while(!confirmPassword.equals(password));
        System.out.println("Enter email:");
        String mail = scanner.nextLine();

        if (userRepository.findByUsername(username).isPresent()) {
            System.out.println("Username already taken.");
            return false;
        }
        Customer newUser = new Customer();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // Encrypt the password
        newUser.setMail(mail);
        userRepository.save(newUser);
        System.out.println("Registration successful.");
        return true;
    }
}
