package com.example.backend.Repository;

import com.example.backend.Beans.Customer;
import com.example.backend.Beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);

}
