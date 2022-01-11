package com.example.backend.Repository;

import com.example.backend.Beans.BoatReservation;
import com.example.backend.Beans.CottageReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface BoatReservationRepository extends JpaRepository<BoatReservation, Long> {
    @Query("select cr from BoatReservation cr where cr.customer.id is null and cr.reservationStart > ?2 ")
    Collection<BoatReservation> getAllBoatReservationInFuture(long customerId , LocalDateTime now);

    @Query("select cr from BoatReservation cr where cr.customer.id = ?1" +
            " and cr.reservationStart > ?2")
    Collection<BoatReservation> getAllReservationOfCustomerInFuture(long customerId , LocalDateTime now);

    @Query("select cr from BoatReservation cr where cr.customer.id = ?1" +
            " and cr.reservationStart <= ?2")
    Collection<BoatReservation> getAllReservationOfCustomerInPast(long customerId , LocalDateTime now);

    @Query("select cr from BoatReservation cr where cr.customer.id = ?1" +
            " and cr.reservationStart < ?2 and cr.complaint is null")
    Collection<BoatReservation> getAllReservationOfCustomerInPastWithoutComplaint(long id, LocalDateTime now);

    @Query("select cr from BoatReservation cr where cr.complaint.reviewed = false")
    Iterable<? extends BoatReservation> getAllNotReviewedBoatComplaint();

    @Query("select cr from BoatReservation cr where cr.boat.id = ?1 and cr.customer is null" +
            " and cr.reservationStart > ?2 and cr.fast = true ")
    Collection<BoatReservation> getAllFreeFutureActionsOfBoat(long id, LocalDateTime now);

    @Query("select cr from BoatReservation cr where cr.boat.boatOwner.id = ?1"+
            " and cr.fast = false")
    Collection<BoatReservation> getAllReservationOfOwner(long customerId);

    @Query("select cr from BoatReservation cr where cr.boat.boatOwner.id = ?1"+
            " and cr.captain = true")
    Collection<BoatReservation> getAllReservationOfCaptain(long customerId);

    @Query("select cr from BoatReservation cr where cr.boat.boatOwner.id = ?1"+
            " and cr.fast = true")
    Collection<BoatReservation> getAllFastReservationOfOwner(long customerId);

    @Query("select cr from BoatReservation cr where" +
            " cr.boat.boatOwner.id = ?1 order by cr.reservationStart asc ")
    Collection<BoatReservation> getAllReservationsOfOwnerForCalendar(long ownerId);

    @Query("select cr from BoatReservation cr where" +
            " cr.boat.id = ?1 order by cr.reservationStart asc ")
    Collection<BoatReservation> getAllReservationsOfBoatForCalendar(long boatId);
}
