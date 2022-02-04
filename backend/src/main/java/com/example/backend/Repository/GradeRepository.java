package com.example.backend.Repository;

import com.example.backend.Beans.Complaint;
import com.example.backend.Beans.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("select g from Grade g where g.reviewed = false")
    Collection<Grade> findAllGradesForRevision();

    @Query("select g from Grade g where g.instructor.id = ?1 and g.enabled = true")
    Collection<Grade> findAllGradesOfInstructor(long id);

    @Query("select g from Grade g where g.cottage.id = ?1 and g.enabled = true")
    Collection<Grade> findAllGradesOfCottage(long id);

    @Query("select g from Grade g where g.cottage.cottageOwner.id = ?1 and g.enabled = true")
    Collection<Grade> findAllGradesOfCottageOwner(long id);

    @Query("select g from Grade g where g.boat.id = ?1 and g.enabled = true")
    Collection<Grade> findAllGradesOfBoat(long id);

    @Query("select g from Grade g where g.boat.boatOwner.id = ?1 and g.enabled = true")
    Collection<Grade> findAllGradesOfBoatOwner(long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select g from Grade g where g.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Grade findOneById(@Param("id") long id);
}
