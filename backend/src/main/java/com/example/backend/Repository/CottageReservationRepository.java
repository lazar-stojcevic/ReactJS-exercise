package com.example.backend.Repository;

import com.example.backend.Beans.CottageReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.time.LocalDateTime;
import java.util.Collection;

public interface CottageReservationRepository extends JpaRepository<CottageReservation, Long> {
    @Query("select cr from CottageReservation cr where cr.cottage.id = ?1 and cr.reservationStart > ?2 ")
    Collection<CottageReservation> getAllCottageReservationInFuture(long cottageId , LocalDateTime now);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select cr from CottageReservation cr where cr.customer.id = ?1 and cr.reservationStart > ?2")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Collection<CottageReservation> getAllReservationOfCustomerInFuture(long customerId , LocalDateTime now);

    @Query("select cr from CottageReservation cr where cr.cottage.cottageOwner.id = ?1"+
            " and cr.fast = false")
    Collection<CottageReservation> getAllReservationOfOwner(long customerId);

    @Query("select cr from CottageReservation cr where cr.cottage.id = ?1 order by cr.reservationStart asc")
    Collection<CottageReservation> getAllReservationOfCottage(long cottageId);

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
    Collection<CottageReservation> getAllNotReviewedCottageComplaint();

    @Query("select cr from CottageReservation cr where cr.cottage.id = ?1 and cr.customer is null" +
            " and cr.reservationStart > ?2 and cr.fast = true ")
    Collection<CottageReservation> getAllFreeFutureActionsOfCottage(long id, LocalDateTime now);

    @Query("select cr from CottageReservation cr where" +
            " cr.cottage.cottageOwner.id = ?1 order by cr.reservationStart asc ")
    Collection<CottageReservation> getAllReservationsOfOwnerForCalendar(long ownerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select cr from CottageReservation cr where cr.cottage.id = ?1 and cr.reservationStart > ?2")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Collection<CottageReservation> getAllFutureCottageReservationOfCottage(long id, LocalDateTime now);
    
    @Query("select cr from CottageReservation cr where cr.customer is not null and cr.reservationStart between ?1 and ?2")
    Collection<CottageReservation> getAllReservationsForCalculatingIncome(LocalDateTime startTime, LocalDateTime endTime);

    @Query("select cr from CottageReservation cr where cr.customer is not null and cr.reservationStart between ?1 and ?2 and cr.cottage.cottageOwner.id = ?3")
    Collection<CottageReservation> getAllReservationsForCalculatingOwnersIncome(LocalDateTime startTime, LocalDateTime endTime, long id);
}
