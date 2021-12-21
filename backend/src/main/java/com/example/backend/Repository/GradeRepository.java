package com.example.backend.Repository;

import com.example.backend.Beans.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    @Query("select g from Grade g where g.enabled = false")
    Collection<Grade> findAllGradesForRevision();

    @Query("select g from Grade g where g.instructor.id = ?1 and g.enabled = true")
    Collection<Grade> findAllGradesOfInstructor(long id);
}
