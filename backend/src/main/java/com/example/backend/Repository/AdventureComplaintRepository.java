package com.example.backend.Repository;

import com.example.backend.Beans.AdventureComplaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdventureComplaintRepository extends JpaRepository<AdventureComplaint, Long> {
}
