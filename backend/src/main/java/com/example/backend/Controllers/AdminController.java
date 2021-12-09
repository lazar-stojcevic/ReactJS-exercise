package com.example.backend.Controllers;

import com.example.backend.Beans.Admin;
import com.example.backend.Dtos.AdminChangeDto;
import com.example.backend.Dtos.PasswordChangeDto;
import com.example.backend.Dtos.UserRegistration;
import com.example.backend.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @GetMapping
    public ResponseEntity<Collection<Admin>> getAll(){
        return new ResponseEntity<>(this.adminService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{adminId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Admin> getAdminById(@PathVariable long adminId){
        return new ResponseEntity<>(adminService.findAdminById(adminId), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> save(@RequestBody UserRegistration userRegistration){
        Admin admin = null;
        try {
            admin = adminService.save(userRegistration);
        }catch (Exception e){
            System.out.println(e);
        }
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Admin> update(@RequestBody AdminChangeDto adminChangeDto){
        return new ResponseEntity<>(adminService.updateAdmin(adminChangeDto), HttpStatus.OK);
    }

    @PutMapping(path = "/changePassword")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto passwordChangeDto){
        adminService.changePassword(passwordChangeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
