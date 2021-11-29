package com.example.backend;

import com.example.backend.Beans.Address;
import com.example.backend.Beans.Customer;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Beans.Role;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Repository.UserRepository;
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

        Role role3 = new Role();
        role3.setName("FISHING_INSTRUCTOR_ROLE");

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

        FishingInstructor fishingInstructor = new FishingInstructor();
        fishingInstructor.setEmail("lupulov@org.com");
        fishingInstructor.setFirstname("Bojan");
        fishingInstructor.setLastName("Lupulov");
        fishingInstructor.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        fishingInstructor.setEnabled(true);
        fishingInstructor.setPhone("566-566");
        fishingInstructor.setLastPasswordResetDate(new Timestamp(2017, 8, 1, 1, 1, 1, 1));
        fishingInstructor.setRole(role3);

        Address address = new Address();
        address.setStreet("Dostojevskog");
        address.setCity("Jarkovac");
        address.setCountry("Srbija");
        FishingInstructor fishingInstructor1 = new FishingInstructor();
        fishingInstructor1.setEmail("nekiMejl@org.com");
        fishingInstructor1.setFirstname("Milan");
        fishingInstructor1.setLastName("Mikic");
        fishingInstructor1.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        fishingInstructor1.setEnabled(false);
        fishingInstructor1.setPhone("566-566");
        fishingInstructor1.setLastPasswordResetDate(new Timestamp(2017, 8, 1, 1, 1, 1, 1));
        fishingInstructor1.setRole(role3);
        fishingInstructor1.setAddress(address);

        //ROLE REPO
        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);

        //USER REPO
        repository.save(customer1);
        repository.save(customer2);
        repository.save(fishingInstructor);
        repository.save(fishingInstructor1);
    }

}
