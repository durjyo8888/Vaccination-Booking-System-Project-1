package com.example.VaccinationBookingSystem.Repository;

import com.example.VaccinationBookingSystem.Model.Dose;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoseRepository extends JpaRepository<Dose, Integer> {
}
