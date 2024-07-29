package com.weaterstats.project3.weatherStats.repositories;

import com.weaterstats.project3.weatherStats.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);
}

