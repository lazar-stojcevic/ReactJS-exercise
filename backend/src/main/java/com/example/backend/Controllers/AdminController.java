package com.example.backend.Controllers;

import com.example.backend.Beans.Admin;
import com.example.backend.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping
    public Collection<Admin> getAll(){
        return this.adminService.getAll();
    }

    @PostMapping(consumes = "application/json")
    public Admin save(@RequestBody Admin admin){
        return this.adminService.save(admin);
    }
}
