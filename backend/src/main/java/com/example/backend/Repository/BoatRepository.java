package com.example.backend.Repository;

import com.example.backend.Beans.Boat;
import com.example.backend.Beans.DeleteProfileRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

public interface BoatRepository extends JpaRepository<Boat, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select boat from Boat boat where boat.id = ?1 ")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    Boat findBoatById( long id);
}
