package com.example.backend;

import com.example.backend.Beans.Customer;
import com.example.backend.Beans.Role;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role role1 = new Role();
        role1.setName("CUSTOMER_ROLE");

        Role role2 = new Role();
        role2.setName("ADMIN_ROLE");

        Customer customer1 = new Customer();
        customer1.setEmail("joxy99@gmail.com");
        customer1.setFirstname("Jovana");
        customer1.setLastName("Stankovic");
        customer1.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        customer1.setEnabled(true);
        customer1.setPhone("555-333");
        customer1.setLastPasswordResetDate(new Timestamp(2015, 13, 1, 1, 1, 1, 1));
        customer1.setRole(role1);


        Customer customer2 = new Customer();
        customer2.setEmail("stojcevic@naaci.com");
        customer2.setFirstname("Lazar");
        customer2.setLastName("Stojcevic");
        customer2.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        customer2.setEnabled(true);
        customer2.setPhone("555-333");
        customer2.setLastPasswordResetDate(new Timestamp(2010, 13, 1, 1, 1, 1, 1));
        customer2.setRole(role2);


        roleRepository.save(role1);
        roleRepository.save(role2);

        repository.save(customer1);
        repository.save(customer2);
    }

}
