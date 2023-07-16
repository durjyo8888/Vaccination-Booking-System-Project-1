package com.example.VaccinationBookingSystem.Repository;

import com.example.VaccinationBookingSystem.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person,Integer> {
}
