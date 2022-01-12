package com.example.backend.Repository;

import com.example.backend.Beans.Tax;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaxRepository extends JpaRepository<Tax, Long> {
    @Query("select t from Tax t where t.current = true")
    Tax getCurrentTax();

}
