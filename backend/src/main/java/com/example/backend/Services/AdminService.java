package com.example.backend.Services;

import com.example.backend.Beans.Address;
import com.example.backend.Beans.Admin;
import com.example.backend.Dtos.AdminChangeDto;
import com.example.backend.Dtos.PasswordChangeDto;
import com.example.backend.Dtos.UserRegistration;
import com.example.backend.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    public Admin save(UserRegistration userRegistration) throws Exception{
        return this.adminRepository.save(createAdmin(userRegistration));
    }

    private Admin createAdmin(UserRegistration userRegistration){
        Admin admin = new Admin();
        admin.setRole(roleService.findByName("ROLE_ADMIN"));
        admin.setEnabled(true);
        Address address = new Address();
        address.setStreet(userRegistration.getStreet());
        address.setCity(userRegistration.getCity());
        address.setCountry(userRegistration.getCountry());
        admin.setAddress(address);
        admin.setEmail(userRegistration.getEmail());
        admin.setFirstTimeCreated(true);
        admin.setFirstname(userRegistration.getFirstname());
        admin.setLastName(userRegistration.getLastname());
        admin.setPhone(userRegistration.getPhone());
        admin.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        admin.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        return admin;
    }

    public Admin updateAdmin(AdminChangeDto adminChangeDto) {
        Admin admin = findAdminById(adminChangeDto.getId());
        admin.setFirstname(adminChangeDto.getFirstname());
        admin.setLastName(adminChangeDto.getLastName());
        admin.setPhone(adminChangeDto.getPhone());
        admin.getAddress().setStreet(adminChangeDto.getStreet());
        admin.getAddress().setCity(adminChangeDto.getCity());
        admin.getAddress().setCountry(adminChangeDto.getCountry());
        return adminRepository.save(admin);
    }

    public void changePassword(PasswordChangeDto passwordChangeDto) {
        Admin admin = findAdminById(passwordChangeDto.getUserId());
        admin.setPassword(passwordEncoder.encode(passwordChangeDto.getPassword()));
        admin.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        admin.setFirstTimeCreated(false);
        adminRepository.save(admin);
    }
}
