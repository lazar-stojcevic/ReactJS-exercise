package com.example.backend.Repository;

import java.util.List;

import com.example.backend.Beans.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {
    List<Role> findByName(String name);
}
