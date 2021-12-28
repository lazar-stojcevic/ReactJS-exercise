package com.example.backend.Repository;

import com.example.backend.Beans.AdventureComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface AdventureComplaintRepository extends JpaRepository<AdventureComplaint, Long> {
    @Query("select ac from AdventureComplaint ac where ac.reviewed = false")
    Collection<AdventureComplaint> getAllNotReviewedAdventureComplaint();
}
