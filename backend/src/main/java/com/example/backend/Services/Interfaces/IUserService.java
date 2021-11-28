package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.Customer;
import com.example.backend.Beans.User;
import com.example.backend.Dtos.UserRegistration;

import java.util.List;

public interface IUserService {
    Customer findById(Long id);
    Customer findByEmail(String email);
    List<Customer> findAll ();
    User save(UserRegistration UserRegistration);
}
