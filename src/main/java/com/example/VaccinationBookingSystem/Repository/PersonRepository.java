package com.example.VaccinationBookingSystem.Repository;

import com.example.VaccinationBookingSystem.Model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person,Integer> {
    Person findByEmailId(String oldEmail);

    List<Person> findByAgeAndName(int age, String name);
}
