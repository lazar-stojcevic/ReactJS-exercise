package com.example.backend.Repository;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;

@Repository
public interface AdventureReservationRepository extends JpaRepository<AdventureReservation, Long> {
    Collection<AdventureReservation> findByreservationStartBetween(LocalDateTime reservationStart, LocalDateTime reservationStart2);

    @Query("select ar from AdventureReservation ar where ar.customer.id = ?1 and ar.rated = false" +
            " and ar.reservationStart < ?2")
    Collection<AdventureReservation> getAllReservationOfCustomerForEvaluation(long customerId , LocalDateTime now);
}
