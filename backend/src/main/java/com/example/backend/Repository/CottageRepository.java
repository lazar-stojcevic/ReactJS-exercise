package com.example.backend.Repository;

import com.example.backend.Beans.Cottage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageRepository extends JpaRepository<Cottage, Long> {
}
