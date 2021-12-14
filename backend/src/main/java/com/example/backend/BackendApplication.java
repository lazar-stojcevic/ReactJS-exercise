package com.example.backend;

import com.example.backend.Beans.*;
import com.example.backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
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
    @Autowired
    private CottageRepository cottageRepository;
    @Autowired
    private AdminRepository adminRepository;

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

        Role role4 = new Role();
        role4.setName("ROLE_BOAT_OWNER");

        Role role5 = new Role();
        role5.setName("ROLE_COTTAGE_OWNER");

        //ROLE REPO
        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);
        roleRepository.save(role4);
        roleRepository.save(role5);

        Customer customer1 = new Customer();
        customer1.setEmail("joxy99@gmail.com");
        customer1.setFirstname("Jovana");
        customer1.setLastName("Stankovic");
        customer1.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        customer1.setEnabled(true);
        customer1.setPhone("555-333");
        customer1.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        Address address8 = new Address();
        address8.setStreet("Nikole Tesle");
        address8.setCity("Karavukovo");
        address8.setCountry("Srbija");
        customer1.setAddress(address8);
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
        AvailableTimespan availableTimespan = new AvailableTimespan();
        availableTimespan.setFromDate(LocalDateTime.of(2022, 10, 10, 8, 0));
        availableTimespan.setToDate(LocalDateTime.of(2022, 2, 10, 15, 0));
        fishingInstructor.setAvailable(availableTimespan);

        Address address = new Address();
        address.setStreet("Dostojevskog");
        address.setCity("Jarkovac");
        address.setCountry("Srbija");
        FishingInstructor fishingInstructor1 = new FishingInstructor();
        fishingInstructor1.setEmail("bojanlupulov0909@gmail.com");
        fishingInstructor1.setFirstname("Milan");
        fishingInstructor1.setLastName("Mikic");
        fishingInstructor1.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        fishingInstructor1.setEnabled(false);
        fishingInstructor1.setPhone("566-566");
        fishingInstructor1.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        fishingInstructor1.setRole(roleRepository.findByName("ROLE_INSTRUCTOR").get(0));
        fishingInstructor1.setAddress(address);


        CottageOwner cottageOwner1 = new CottageOwner();
        cottageOwner1.setRole(role5);
        cottageOwner1.setEmail("teddyjj25@gmail.com");
        cottageOwner1.setFirstname("Marija");
        cottageOwner1.setLastName("Markovic");
        cottageOwner1.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        cottageOwner1.setEnabled(true);
        cottageOwner1.setPhone("0605412876");
        cottageOwner1.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        Address address2 = new Address();
        address2.setCountry("Srbija");
        address2.setCity("Sabac");
        address2.setStreet("Pop Lukina");
        cottageOwner1.setAddress(address2);

        CottageOwner cottageOwner2 = new CottageOwner();
        cottageOwner2.setRole(role5);
        cottageOwner2.setEmail("mejlic55@gmail.com");
        cottageOwner2.setFirstname("Petar");
        cottageOwner2.setLastName("Lazic");
        cottageOwner2.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        cottageOwner2.setEnabled(false);
        cottageOwner2.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        cottageOwner2.setPhone("0605412875");
        Address address3 = new Address();
        address3.setCountry("Srbija");
        address3.setCity("Sabac");
        address3.setStreet("Milosa Stojicevica Pocerca");
        cottageOwner2.setAddress(address3);

        BoatOwner boatOwner1 = new BoatOwner();
        boatOwner1.setRole(role4);
        boatOwner1.setEmail("mejl55@gmail.com");
        boatOwner1.setFirstname("Marko");
        boatOwner1.setLastName("Stefanovic");
        boatOwner1.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        boatOwner1.setEnabled(true);
        boatOwner1.setPhone("0605412412");
        boatOwner1.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        Address address4 = new Address();
        address4.setCountry("Srbija");
        address4.setCity("Sabac");
        address4.setStreet("Vojvode Misica");
        boatOwner1.setAddress(address4);

        BoatOwner boatOwner2 = new BoatOwner();
        boatOwner2.setRole(role4);
        boatOwner2.setEmail("mejl45@gmail.com");
        boatOwner2.setFirstname("Milica");
        boatOwner2.setLastName("Kosanic");
        boatOwner2.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        boatOwner2.setEnabled(false);
        boatOwner2.setPhone("0605458412");
        boatOwner2.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        Address address5 = new Address();
        address5.setCountry("Srbija");
        address5.setCity("Sabac");
        address5.setStreet("Jovana Cvijica");
        boatOwner2.setAddress(address5);

        //USER REPO
        repository.save(customer1);
        repository.save(customer2);
        repository.save(fishingInstructor);
        repository.save(fishingInstructor1);
        repository.save(cottageOwner1);
        repository.save(cottageOwner2);
        repository.save(boatOwner1);
        repository.save(boatOwner2);

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
        PriceList priceList = new PriceList();
        priceList.setPrice(4000);
        adventure.setPriceList(priceList);

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
        PriceList priceList1 = new PriceList();
        priceList1.setPrice(5000);
        adventure1.setPriceList(priceList1);

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

        Cottage cottage1 = new Cottage();
        cottage1.setConductRules("Budite fini");
        cottage1.setName("Mala kuca");
        cottage1.setPromo("Kod nas ce biti lud provod");
        cottage1.setRating(4.1);
        Address address6 = new Address();
        address6.setCity("Zlatibor");
        address6.setCountry("Srbija");
        address6.setStreet("Ustanicka");
        cottage1.setAddress(address6);
        cottage1.setCottageOwner(cottageOwner1);
        PriceList priceList14 = new PriceList();
        priceList14.setPrice(5000);
        cottage1.setPriceList(priceList14);

        Cottage cottage2 = new Cottage();
        cottage2.setConductRules("Budite fini");
        cottage2.setName("Mala kuca");
        cottage2.setPromo("Opustena atmosvera u prirodi");
        cottage2.setRating(3.3);
        Address address7 = new Address();
        address7.setCity("Tara");
        address7.setCountry("Srbija");
        address7.setStreet("Ustanicka");
        cottage2.setAddress(address7);
        cottage2.setCottageOwner(cottageOwner2);

        cottageRepository.save(cottage1);
        cottageRepository.save(cottage2);

        Admin admin = new Admin();
        Address adminAddress = new Address();
        adminAddress.setStreet("Svetosavska 10");
        adminAddress.setCity("Jarkovac");
        adminAddress.setCountry("Srbija");
        admin.setFirstTimeCreated(false);
        admin.setAddress(adminAddress);
        admin.setRole(role2);
        admin.setEmail("qwe@gmail.com");
        admin.setFirstname("Stojan");
        admin.setLastName("Stojanovic");
        admin.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        admin.setEnabled(true);
        admin.setPhone("555-333");
        admin.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));

        adminRepository.save(admin);
    }

}
