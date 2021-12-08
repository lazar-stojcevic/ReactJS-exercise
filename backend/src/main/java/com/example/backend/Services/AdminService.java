package com.example.backend.Services;

import com.example.backend.Beans.Admin;
import com.example.backend.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    public Collection<Admin> getAll(){
        return this.adminRepository.findAll();
    }

    public Admin findAdminById(long adminId){
        return adminRepository.findById(adminId).orElse(null);
    }

    public Admin save(Admin admin){
        return this.adminRepository.save(admin);
    }

}
