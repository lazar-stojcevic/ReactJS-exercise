package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.Role;

import java.util.List;

public interface IRoleService {
    Role findById(Long id);
    Role findByName(String name);
}
