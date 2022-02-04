package com.example.backend.Repository;

import com.example.backend.Beans.ForbiddenCustomerToBoat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface ForbiddenCustomerToBoatRepository extends JpaRepository<ForbiddenCustomerToBoat, Long> {
    @Query("select fc from ForbiddenCustomerToBoat fc where fc.customer.id = ?1" +
            " and fc.boat.id = ?2")
    Collection<ForbiddenCustomerToBoat> getAllCancellationsOfCustomerToBoat(long customerId , long cottageId);
}
