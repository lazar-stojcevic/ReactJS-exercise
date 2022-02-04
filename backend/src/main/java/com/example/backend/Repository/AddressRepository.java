package com.example.backend.Repository;

import com.example.backend.Beans.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
