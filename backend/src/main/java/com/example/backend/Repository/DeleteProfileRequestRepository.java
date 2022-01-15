package com.example.backend.Repository;

import com.example.backend.Beans.DeleteProfileRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Collection;

public interface DeleteProfileRequestRepository extends JpaRepository<DeleteProfileRequest, Long> {
    @Query("select dpr from DeleteProfileRequest dpr where dpr.reviewed = false")
    Collection<DeleteProfileRequest> getAllNotReviewedRequestsForProfileDeleting();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select dpr from DeleteProfileRequest dpr where dpr.id = :id")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    DeleteProfileRequest findOneById(@Param("id") long id);
}
