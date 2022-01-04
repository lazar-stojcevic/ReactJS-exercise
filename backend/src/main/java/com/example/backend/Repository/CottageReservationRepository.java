package com.example.backend.Repository;

import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.CottageReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageReservationRepository extends JpaRepository<CottageReservation, Long> {
}
