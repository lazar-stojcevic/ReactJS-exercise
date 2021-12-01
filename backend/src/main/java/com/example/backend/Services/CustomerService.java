package com.example.backend.Services;

import com.example.backend.Beans.Address;
import com.example.backend.Beans.Customer;
import com.example.backend.Beans.Role;
import com.example.backend.Dtos.UserRegistration;
import com.example.backend.Repository.AddressRepository;
import com.example.backend.Repository.CustomerRepository;
import com.example.backend.Services.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerService implements IUserService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.getById(id);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer save(UserRegistration userRegistration) {
        Customer u = new Customer();

        u.setEmail(userRegistration.getEmail());

        // pre nego sto postavimo lozinku u atribut hesiramo je kako bi se u bazi nalazila hesirana lozinka
        // treba voditi racuna da se koristi isi password encoder bean koji je postavljen u AUthenticationManager-u kako bi koristili isti algoritam
        //System.out.println(userRegistration);
        u.setPassword(passwordEncoder.encode(userRegistration.getPassword()));

        u.setFirstname(userRegistration.getFirstname());
        u.setLastName(userRegistration.getLastname());
        u.setEnabled(false); //Za sad je true dok se ne doda slanje na mejl
        u.setEmail(userRegistration.getEmail());
        u.setPhone(userRegistration.getPhone());
        u.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        Address address = new Address();
            address.setCity(userRegistration.getCity());
            address.setStreet(userRegistration.getStreet());
            address.setCountry(userRegistration.getCountry());

        addressRepository.save(address);
        u.setAddress(address);

        // u primeru se registruju samo obicni korisnici i u skladu sa tim im se i dodeljuje samo rola USER
        Role roles = roleService.findByName("CUSTOMER_ROLE");
        u.setRole(roles);

        return this.customerRepository.save(u);
    }

    public Customer verifyUser(Customer customer){
        customer.setEnabled(true);
        return this.customerRepository.save(customer);
    }
}
