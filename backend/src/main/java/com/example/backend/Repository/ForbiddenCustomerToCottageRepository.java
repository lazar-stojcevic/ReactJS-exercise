package com.example.backend.Repository;

import com.example.backend.Beans.CottageReservation;
import com.example.backend.Beans.ForbiddenCustomerToCottage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface ForbiddenCustomerToCottageRepository extends JpaRepository<ForbiddenCustomerToCottage, Long> {
    @Query("select fc from ForbiddenCustomerToCottage fc where fc.customer.id = ?1" +
            " and fc.cottage.id = ?2")
    Collection<ForbiddenCustomerToCottage> getAllCancellationsOfCustomerToCottage(long customerId , long cottageId);
}
