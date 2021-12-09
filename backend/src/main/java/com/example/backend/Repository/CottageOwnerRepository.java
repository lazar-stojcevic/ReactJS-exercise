package com.example.backend.Repository;

import com.example.backend.Beans.CottageOwner;
import com.example.backend.Beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CottageOwnerRepository  extends JpaRepository<CottageOwner, Long> {
}
