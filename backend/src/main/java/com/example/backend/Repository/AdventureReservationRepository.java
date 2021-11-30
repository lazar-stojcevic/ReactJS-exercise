package com.example.backend.Repository;

import com.example.backend.Beans.AdventureReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdventureReservationRepository extends JpaRepository<AdventureReservation, Long> {
}
