package com.example.backend.Repository;

import com.example.backend.Beans.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    @Query("select r from Report r where r.processed = false")
    Collection<Report> getAllNotProcessedReports();
}
