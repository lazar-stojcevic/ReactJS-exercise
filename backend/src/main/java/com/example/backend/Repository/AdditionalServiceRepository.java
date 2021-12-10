package com.example.backend.Repository;

import com.example.backend.Beans.AdditionalServices;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdditionalServiceRepository extends JpaRepository<AdditionalServices, Long> {
}
