package com.example.backend.Repository;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.AvailablePeriodCottage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvailablePeriodCottageRepository extends JpaRepository<AvailablePeriodCottage, Long> {
}
