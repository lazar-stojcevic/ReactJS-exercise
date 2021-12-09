package com.example.backend.Services;

import com.example.backend.Beans.Address;
import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.CottageOwner;
import com.example.backend.Beans.Customer;
import com.example.backend.Dtos.CottageOwnerChangeDto;
import com.example.backend.Dtos.UserRegistration;
import com.example.backend.Repository.AddressRepository;
import com.example.backend.Repository.CottageOwnerRepository;
import com.example.backend.Repository.CustomerRepository;
import com.example.backend.Services.Interfaces.ICottageOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class CottageOwnerService implements ICottageOwnerService {

    @Autowired
    private CottageOwnerRepository cottageOwnerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    public CottageOwnerService(CottageOwnerRepository cottageOwnerRepository){
        this.cottageOwnerRepository = cottageOwnerRepository;
    }

    @Override
    public Collection<CottageOwner> getAllCottageOwners() {
        return cottageOwnerRepository.findAll();
    }

    @Override
    public CottageOwner saveCottageOwner(UserRegistration userRegistration) throws Exception {
        CottageOwner cottageOwner = new CottageOwner();
        cottageOwner.setFirstname(userRegistration.getFirstname());
        cottageOwner.setLastName(userRegistration.getLastname());
        Address address = new Address();
        address.setCity(userRegistration.getCity());
        address.setStreet(userRegistration.getStreet());
        address.setCountry(userRegistration.getCountry());
        addressRepository.save(address);
        cottageOwner.setAddress(address);
        cottageOwner.setPhone(userRegistration.getPhone());
        cottageOwner.setEmail(userRegistration.getEmail());
        cottageOwner.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
        cottageOwner.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        cottageOwner.setEnabled(false);
        cottageOwner.setRole(roleService.findByName("COTTAGE_OWNER_ROLE"));
        return  cottageOwnerRepository.save(cottageOwner);
    }

    @Override
    public CottageOwner findCottageOwner(long id) {
        Optional<CottageOwner> cottageOwner = cottageOwnerRepository.findById(id);
        return cottageOwner.orElse(null);
    }

    @Override
    public CottageOwner updateCottageOwner(CottageOwnerChangeDto changeDto) {
        CottageOwner cottageOwner = findCottageOwner(changeDto.getId());
        cottageOwner.setFirstname(changeDto.getFirstname());
        cottageOwner.setLastName(changeDto.getLastName());
        cottageOwner.getAddress().setStreet(changeDto.getStreet());
        cottageOwner.getAddress().setCity(changeDto.getCity());
        cottageOwner.getAddress().setCountry(changeDto.getCountry());
        cottageOwner.setPhone(changeDto.getPhone());
        return cottageOwnerRepository.save(cottageOwner);
    }

    @Override
    public CottageOwner enableCottageOwner(long id) {
        CottageOwner cottageOwner = findCottageOwner(id);
        cottageOwner.setEnabled(true);
        return cottageOwnerRepository.save(cottageOwner);
    }

    @Override
    public void disableCottageOwner(long id) {
        CottageOwner cottageOwner = findCottageOwner(id);
        cottageOwner.setEnabled(false);
        cottageOwnerRepository.save(cottageOwner);
    }

    @Override
    public void deleteCottageOwner(long id) {
        cottageOwnerRepository.deleteById(id);
    }
}
