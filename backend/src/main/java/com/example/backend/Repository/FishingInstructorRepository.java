package com.example.backend.Repository;

import com.example.backend.Beans.FishingInstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FishingInstructorRepository extends JpaRepository<FishingInstructor, Long> {

}
