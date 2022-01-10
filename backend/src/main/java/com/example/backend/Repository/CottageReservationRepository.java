package com.example.backend.Repository;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.CottageReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface CottageReservationRepository extends JpaRepository<CottageReservation, Long> {
    @Query("select cr from CottageReservation cr where cr.customer.id is null and cr.reservationStart > ?2 ")
    Collection<CottageReservation> getAllCottageReservationInFuture(long customerId , LocalDateTime now);

    @Query("select cr from CottageReservation cr where cr.customer.id = ?1" +
            " and cr.reservationStart > ?2")
    Collection<CottageReservation> getAllReservationOfCustomerInFuture(long customerId , LocalDateTime now);

    @Query("select cr from CottageReservation cr where cr.cottage.cottageOwner.id = ?1"+
            " and cr.fast = false")
    Collection<CottageReservation> getAllReservationOfOwner(long customerId);

    @Query("select cr from CottageReservation cr where cr.cottage.cottageOwner.id = ?1"+
            " and cr.fast = true")
    Collection<CottageReservation> getAllFastReservationOfOwner(long customerId);

    @Query("select cr from CottageReservation cr where cr.customer.id = ?1" +
            " and cr.reservationStart <= ?2")
    Collection<CottageReservation> getAllReservationOfCustomerInPast(long customerId , LocalDateTime now);

    @Query("select cr from CottageReservation cr where cr.customer.id = ?1" +
            " and cr.reservationStart < ?2 and cr.complaint is null")
    Collection<CottageReservation> getAllReservationOfCustomerInPastWithoutComplaint(long id, LocalDateTime now);

    @Query("select cr from CottageReservation cr where cr.complaint.reviewed = false")
    Iterable<? extends CottageReservation> getAllNotReviewedCottageComplaint();

    @Query("select cr from CottageReservation cr where cr.cottage.id = ?1 and cr.customer is null" +
            " and cr.reservationStart > ?2 and cr.fast = true ")
    Collection<CottageReservation> getAllFreeFutureActionsOfCottage(long id, LocalDateTime now);
}
