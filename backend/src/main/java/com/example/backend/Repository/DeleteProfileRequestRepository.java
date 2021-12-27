package com.example.backend.Repository;

import com.example.backend.Beans.DeleteProfileRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface DeleteProfileRequestRepository extends JpaRepository<DeleteProfileRequest, Long> {
    @Query("select dpr from DeleteProfileRequest dpr where dpr.reviewed = false")
    Collection<DeleteProfileRequest> getAllNotReviewedRequestsForProfileDeleting();
}
