package com.example.backend.Repository;

import com.example.backend.Beans.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("select u from User u where u.role.name not like 'ROLE_ADMIN'")
    Collection<User> getAllUsersExceptAdmins();

}
