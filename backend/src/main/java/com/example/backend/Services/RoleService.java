package com.example.backend.Services;

import com.example.backend.Beans.Role;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Services.Interfaces.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findById(Long id) {
        return this.roleRepository.getOne(id);
    }

    @Override
    public Role findByName(String name) {
        List<Role> roles = this.roleRepository.findByName(name);
        return roles.get(0);
    }

    public Role save(Role role){
       return this.roleRepository.save(role);
    }


}
