package com.example.backend.Repository;

import com.example.backend.Beans.Boat;
import com.example.backend.Beans.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select cottage from Cottage cottage where cottage.id = ?1 ")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Cottage findCottageById(long id);
}
