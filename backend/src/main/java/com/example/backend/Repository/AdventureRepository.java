package com.example.backend.Repository;

import com.example.backend.Beans.Adventure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface AdventureRepository extends JpaRepository<Adventure, Long> {
}
