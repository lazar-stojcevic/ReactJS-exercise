package com.example.backend;

import com.example.backend.Beans.*;
import com.example.backend.Repository.AdventureRepository;
import com.example.backend.Repository.AdventureReservationRepository;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootApplication
public class BackendApplication implements CommandLineRunner {
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AdventureRepository adventureRepository;
    @Autowired
    private AdventureReservationRepository adventureReservationRepository;
    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role role1 = new Role();
        role1.setName("ROLE_CUSTOMER");

        Role role2 = new Role();
        role2.setName("ROLE_ADMIN");

        Role role3 = new Role();
        role3.setName("ROLE_INSTRUCTOR");

        //ROLE REPO
        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);

        Customer customer1 = new Customer();
        customer1.setEmail("joxy99@gmail.com");
        customer1.setFirstname("Jovana");
        customer1.setLastName("Stankovic");
        customer1.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        customer1.setEnabled(true);
        customer1.setPhone("555-333");
        customer1.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        customer1.setRole(role1);
        customer1.setPoints(150);
        customer1.setPenaltyPoints(0);

        Customer customer2 = new Customer();
        customer2.setEmail("stole@naaci.com");
        customer2.setFirstname("Lazar");
        customer2.setLastName("Stojcevic");
        customer2.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        customer2.setEnabled(true);
        customer2.setPhone("555-333");
        customer2.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        Address address0 = new Address();
        address0.setStreet("Nikole Tesle");
        address0.setCity("Karavukovo");
        address0.setCountry("Srbija");
        customer2.setAddress(address0);
        customer2.setRole(role1);
        customer2.setPoints(10);
        customer2.setPenaltyPoints(2);

        FishingInstructor fishingInstructor = new FishingInstructor();
        fishingInstructor.setEmail("asd@gmail.com");
        fishingInstructor.setFirstname("Bojan");
        fishingInstructor.setLastName("Lupulov");
        fishingInstructor.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        fishingInstructor.setEnabled(true);
        fishingInstructor.setPhone("566-566");
        fishingInstructor.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        fishingInstructor.setRole(roleRepository.findByName("ROLE_INSTRUCTOR").get(0));
        Address address1 = new Address();
        address1.setStreet("Jaksica");
        address1.setCity("Jarkovac");
        address1.setCountry("Srbija");
        fishingInstructor.setAddress(address1);
        HolidayTimespan holidayTimespan = new HolidayTimespan();
        holidayTimespan.setFromDate(LocalDate.of(2021, 12, 25));
        holidayTimespan.setToDate(LocalDate.of(2022, 1, 10));
        fishingInstructor.setHoliday(holidayTimespan);

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
        fishingInstructor1.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        fishingInstructor1.setRole(roleRepository.findByName("ROLE_INSTRUCTOR").get(0));
        fishingInstructor1.setAddress(address);

        //USER REPO
        repository.save(customer1);
        repository.save(customer2);
        repository.save(fishingInstructor);
        repository.save(fishingInstructor1);

        //ADVENTURE
        Adventure adventure = new Adventure();
        Address adventureAddress = new Address();
        adventureAddress.setStreet("Neka ulica 5");
        adventureAddress.setCity("Neki grad");
        adventureAddress.setCountry("Neka drzava");
        adventure.setAddress(adventureAddress);
        adventure.setName("Izlet na jezeru");
        adventure.setInstructor((FishingInstructor) repository.findByEmail("asd@gmail.com"));
        adventure.setMaxPersons(10);
        adventure.setDescription("Poseta rezervatu prirode i etno selima u blizini");
        adventure.setCancelingTerms("10% uplate zadrzava instruktor");
        adventure.setInstructorBiography("Diplomirani biolog i veliki ljubitelj prirode");
        adventure.setConductRules("Strogo je zabranjeno bacanje djubreta van kanti za smece");
        adventure.setEquipment("Dvogledi i durbini");

        Adventure adventure1 = new Adventure();
        Address adventureAddress1 = new Address();
        adventureAddress1.setStreet("Svetosavska 5");
        adventureAddress1.setCity("Jarkovac");
        adventureAddress1.setCountry("Srbija");
        adventure1.setAddress(adventureAddress1);
        adventure1.setName("Pecanje na kanalu DTD");
        adventure1.setInstructor((FishingInstructor) repository.findByEmail("asd@gmail.com"));
        adventure1.setMaxPersons(4);
        adventure1.setDescription("Izlet pokraj prelepog kanala Dunav-Tisa-Dunav, uz sve cari pecanja i uzivanja u prirodi i vodi");
        adventure1.setCancelingTerms("15% uplate zadrzava instruktor");
        adventure1.setInstructorBiography("Programer u pokusaju :D");
        adventure1.setConductRules("Strogo je zabranjeno bacanje djubreta van kanti za smece, kao i paljenje vatre u prirodi");
        adventure1.setEquipment("Teleskop i blinker");

        adventureRepository.save(adventure);
        adventureRepository.save(adventure1);

        AdventureReservation reservation = new AdventureReservation();
        reservation.setAdventure(adventure);
        reservation.setLength(6);
        reservation.setPrice(5000);
        reservation.setReservationStart(LocalDateTime.now());

        AdventureReservation reservation1 = new AdventureReservation();
        reservation1.setAdventure(adventure);
        reservation1.setLength(6);
        reservation1.setPrice(5000);
        reservation1.setReservationStart(LocalDateTime.of(2020, 10,2,10,20));
        reservation1.setReserved(true);

        AdventureReservation reservation2 = new AdventureReservation();
        reservation2.setAdventure(adventure);
        reservation2.setLength(4);
        reservation2.setPrice(2500);
        reservation2.setReservationStart(LocalDateTime.of(2022, 2,2,10,20));
        reservation2.setReserved(true);

        adventureReservationRepository.save(reservation1);
        adventureReservationRepository.save(reservation);
        adventureReservationRepository.save(reservation2);
    }

}
