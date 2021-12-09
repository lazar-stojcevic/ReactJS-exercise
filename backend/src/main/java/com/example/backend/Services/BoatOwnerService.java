package com.example.backend.Services;

import com.example.backend.Beans.Address;
import com.example.backend.Beans.BoatOwner;
import com.example.backend.Beans.CottageOwner;
import com.example.backend.Dtos.BoatOwnerChangeDto;
import com.example.backend.Dtos.UserRegistration;
import com.example.backend.Repository.AddressRepository;
import com.example.backend.Repository.BoatOwnerRepository;
import com.example.backend.Repository.CottageOwnerRepository;
import com.example.backend.Services.Interfaces.IBoatOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

public class BoatOwnerService implements IBoatOwnerService {

    @Autowired
    private BoatOwnerRepository boatOwnerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    public BoatOwnerService(BoatOwnerRepository boatOwnerRepository){
        this.boatOwnerRepository = boatOwnerRepository;
    }
    @Override
    public Collection<BoatOwner> getAllBoatOwners() {
        return boatOwnerRepository.findAll();
    }

    @Override
    public BoatOwner saveBoatOwner(UserRegistration userRegistration) throws Exception {
        BoatOwner boatOwner = new BoatOwner();
        boatOwner.setFirstname(userRegistration.getFirstname());
        boatOwner.setLastName(userRegistration.getLastname());
        Address address = new Address();
        address.setCity(userRegistration.getCity());
        address.setStreet(userRegistration.getStreet());
        address.setCountry(userRegistration.getCountry());
        addressRepository.save(address);
        boatOwner.setAddress(address);
        boatOwner.setPhone(userRegistration.getPhone());
        boatOwner.setEmail(userRegistration.getEmail());
        boatOwner.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        boatOwner.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        boatOwner.setEnabled(false);
        boatOwner.setRole(roleService.findByName("BOAT_OWNER_ROLE"));
        return  boatOwnerRepository.save(boatOwner);
    }

    @Override
    public BoatOwner findBoatOwner(long id) {
        Optional<BoatOwner> boatOwner = boatOwnerRepository.findById(id);
        return boatOwner.orElse(null);
    }

    @Override
    public BoatOwner updateBoatOwner(BoatOwnerChangeDto changeDto) {
        BoatOwner boatOwner = findBoatOwner(changeDto.getId());
        boatOwner.setFirstname(changeDto.getFirstname());
        boatOwner.setLastName(changeDto.getLastName());
        boatOwner.getAddress().setStreet(changeDto.getStreet());
        boatOwner.getAddress().setCity(changeDto.getCity());
        boatOwner.getAddress().setCountry(changeDto.getCountry());
        boatOwner.setPhone(changeDto.getPhone());
        return boatOwnerRepository.save(boatOwner);
    }

    @Override
    public BoatOwner enableBoatOwner(long id) {
        BoatOwner boatOwner = findBoatOwner(id);
        boatOwner.setEnabled(true);
        return boatOwnerRepository.save(boatOwner);
    }

    @Override
    public void disableBoatOwner(long id) {
        BoatOwner boatOwner = findBoatOwner(id);
        boatOwner.setEnabled(false);
        boatOwnerRepository.save(boatOwner);
    }

    @Override
    public void deleteBoatOwner(long id) {
        boatOwnerRepository.deleteById(id);
    }
}
