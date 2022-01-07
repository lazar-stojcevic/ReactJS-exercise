package com.example.backend;

import com.example.backend.Beans.*;
import com.example.backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private DeleteProfileRequestRepository deleteProfileRequestRepository;
    @Autowired
    private AdventureComplaintRepository adventureComplaintRepository;
    @Autowired
    private AvailablePeriodCottageRepository availablePeriodCottageRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private CottagePriceListRepository cottagePriceListRepository;

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
        customer1.setEmail("yoxy99@gmail.com");
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

        //TESTIRANJE PRETPLATE NA ODREDJENU AVANTURU OD STRANE KORISNIKA
        Customer prepaidCustomer = new Customer();
        prepaidCustomer.setEmail("mihatufa@gmail.com");
        prepaidCustomer.setFirstname("Mihajlo");
        prepaidCustomer.setLastName("Tufic");
        prepaidCustomer.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        prepaidCustomer.setEnabled(true);
        prepaidCustomer.setPhone("555-333");
        prepaidCustomer.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        Address prepaidAddress = new Address();
        prepaidAddress.setStreet("Nikole Tesle");
        prepaidAddress.setCity("Dobanovci");
        prepaidAddress.setCountry("Srbija");
        prepaidCustomer.setAddress(prepaidAddress);
        prepaidCustomer.setRole(role1);
        prepaidCustomer.setPoints(100);
        prepaidCustomer.setPenaltyPoints(0);
        repository.save(prepaidCustomer);

        FishingInstructor fishingInstructor = new FishingInstructor();
        fishingInstructor.setEmail("asd@gmail.com");
        fishingInstructor.setFirstname("Bojan");
        fishingInstructor.setLastName("Lupulov");
        fishingInstructor.setPassword("$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra");
        fishingInstructor.setEnabled(true);
        fishingInstructor.setPhone("566-566");
        fishingInstructor.setLastPasswordResetDate(Timestamp.valueOf(LocalDateTime.now()));
        fishingInstructor.setRole(roleRepository.findByName("ROLE_INSTRUCTOR").get(0));
        fishingInstructor.getPrepaidCustomers().add(prepaidCustomer);
        Address address1 = new Address();
        address1.setStreet("Jaksica");
        address1.setCity("Jarkovac");
        address1.setCountry("Srbija");
        fishingInstructor.setAddress(address1);
        AvailableTimespan availableTimespan = new AvailableTimespan();
        availableTimespan.setFromDate(LocalDateTime.of(2021, 12, 10, 8, 0));
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

        AdditionalService additionalService = new AdditionalService();
        additionalService.setName("Prvi dodatni servis");
        additionalService.setAddPrice(1000);
        additionalService.setPriceList(priceList);

        AdditionalService additionalService2 = new AdditionalService();
        additionalService2.setName("Drugi dodatni servis");
        additionalService2.setAddPrice(500);
        additionalService2.setPriceList(priceList);

        Set<AdditionalService> serviceList = new HashSet<>();
        serviceList.add(additionalService);
        serviceList.add(additionalService2);
        priceList.setAdditionalServices(serviceList);

        adventureRepository.save(adventure);

        //ADVENTURE 2
        Adventure adventure2 = new Adventure();
        Address adventureAddress2 = new Address();
        adventureAddress2.setStreet("Gagarinova");
        adventureAddress2.setCity("Paracin");
        adventureAddress2.setCountry("Srbija");
        adventure2.setAddress(adventureAddress2);
        adventure2.setName("Izlet na jezeru");
        adventure2.setInstructor((FishingInstructor) repository.findByEmail("asd@gmail.com"));
        adventure2.setMaxPersons(10);
        adventure2.setDescription("Poseta etno parku, imitacija divljeg zapada, ludilo");
        adventure2.setCancelingTerms("10% uplate zadrzava instruktor");
        adventure2.setInstructorBiography("Diplomirani biolog i elektro inzenjer");
        adventure2.setConductRules("Strogo je zabranjeno bacanje djubreta van kanti za smece");
        adventure2.setEquipment("Sovijetska vojna oprema.");

        PriceList priceList2 = new PriceList();
        priceList2.setPrice(4000);
        adventure2.setPriceList(priceList2);

        AdditionalService additionalService22 = new AdditionalService();
        additionalService22.setName("Prvi dodatni servis");
        additionalService22.setAddPrice(1000);
        additionalService22.setPriceList(priceList2);

        AdditionalService additionalService23 = new AdditionalService();
        additionalService23.setName("Drugi dodatni servis");
        additionalService23.setAddPrice(500);
        additionalService23.setPriceList(priceList2);

        Set<AdditionalService> serviceList2 = new HashSet<>();
        serviceList2.add(additionalService22);
        serviceList2.add(additionalService23);
        priceList2.setAdditionalServices(serviceList2);

        adventureRepository.save(adventure2);

        AdventureReservation reservation2 = new AdventureReservation();
        reservation2.setAdventure(adventure);
        reservation2.setLength(4);
        //reservation2.setPrice(2500);
        reservation2.setReservationStart(LocalDateTime.of(2022, 1,10,10,20));
        reservation2.setLastDateToReserve(LocalDateTime.of(2021, 12, 31, 15, 0));
        adventureReservationRepository.save(reservation2);

        AdventureReservation reservation3 = new AdventureReservation();
        reservation3.setAdventure(adventure);
        reservation3.setLength(4);
        reservation3.setPrice(2500);
        reservation3.setReservationStart(LocalDateTime.of(2021, 10,10,10,20));
        reservation3.setLastDateToReserve(LocalDateTime.of(2021, 9, 30, 15, 0));
        adventureReservationRepository.save(reservation3);

        AdventureReservation reservation4 = new AdventureReservation();
        reservation4.setAdventure(adventure);
        reservation4.setLength(4);
        reservation4.setPrice(2500);
        reservation4.setReservationStart(LocalDateTime.of(2022, 1,15,10,20));
        reservation4.setLastDateToReserve(LocalDateTime.of(2021, 12, 25, 15, 0));
        adventureReservationRepository.save(reservation4);

        AdventureReservation reservation5 = new AdventureReservation();
        reservation5.setAdventure(adventure);
        reservation5.setLength(4);
        reservation5.setPrice(2500);
        reservation5.setReservationStart(LocalDateTime.of(2022, 1,17,10,20));
        reservation5.setLastDateToReserve(LocalDateTime.of(2021, 12, 30, 15, 0));
        adventureReservationRepository.save(reservation5);

        AdventureReservation reservation9 = new AdventureReservation();
        reservation9.setAdventure(adventure);
        reservation9.setLength(4);
        reservation9.setPrice(2500);
        reservation9.setReservationStart(LocalDateTime.of(2022, 5,17,10,20));
        reservation9.setLastDateToReserve(LocalDateTime.of(2022, 1, 30, 15, 0));
        adventureReservationRepository.save(reservation9);

        AdventureReservation reservation10 = new AdventureReservation();
        reservation10.setAdventure(adventure);
        reservation10.setLength(4);
        reservation10.setPrice(2500);
        reservation10.setReservationStart(LocalDateTime.of(2022, 4,17,10,20));
        reservation10.setLastDateToReserve(LocalDateTime.of(2022, 1, 30, 15, 0));
        adventureReservationRepository.save(reservation10);

        AdventureReservation reservation11 = new AdventureReservation();
        reservation11.setAdventure(adventure);
        reservation11.setLength(4);
        reservation11.setPrice(2500);
        reservation11.setReservationStart(LocalDateTime.of(2022, 9,17,10,20));
        reservation11.setLastDateToReserve(LocalDateTime.of(2022, 1, 30, 15, 0));
        adventureReservationRepository.save(reservation11);

        //OVO JE SAMO ZA PROVERU
        AdventureReservation reservation12 = new AdventureReservation();
        reservation12.setAdventure(adventure2);
        reservation12.setLength(48);
        reservation12.setPrice(2500);
        reservation12.setReservationStart(LocalDateTime.of(2022, 9,25,10,20));
        reservation12.setLastDateToReserve(LocalDateTime.of(2022, 1, 30, 15, 0));
        adventureReservationRepository.save(reservation12);

        //Za proveru zalbi
        AdventureReservation reservation6 = new AdventureReservation();
        reservation6.setAdventure(adventure);
        reservation6.setLength(4);
        reservation6.setPrice(2500);
        reservation6.setReservationStart(LocalDateTime.of(2021, 1,17,10,20));
        reservation6.setLastDateToReserve(LocalDateTime.of(2020, 12, 30, 15, 0));
        reservation6.setCustomer(customer2);
        adventureReservationRepository.save(reservation6);

        AdventureReservation reservation7 = new AdventureReservation();
        reservation7.setAdventure(adventure);
        reservation7.setLength(4);
        reservation7.setPrice(2500);
        reservation7.setReservationStart(LocalDateTime.of(2021, 2,17,10,20));
        reservation7.setLastDateToReserve(LocalDateTime.of(2020, 12, 30, 15, 0));
        reservation7.setCustomer(customer2);
        adventureReservationRepository.save(reservation7);

        //Za proveru akcija
        AdventureReservation reservation8 = new AdventureReservation();
        reservation8.setAdventure(adventure);
        reservation8.setLength(4);
        reservation8.setPrice(1500);
        reservation8.setReservationStart(LocalDateTime.of(2022, 3,17,10,20));
        reservation8.setLastDateToReserve(LocalDateTime.of(2022, 2, 15, 15, 0));
        reservation8.setDiscount(20);
        adventureReservationRepository.save(reservation8);

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

        CottagePriceList priceList14 = new CottagePriceList();
        priceList14.setPrice(5000);
        cottage1.setCottagePriceList(priceList14);

        AdditionalCottageService additionalService33 = new AdditionalCottageService();
        additionalService33.setName("Trening golfa");
        additionalService33.setAddPrice(1000);
        additionalService33.setPriceList(priceList14);

        AdditionalCottageService additionalService34 = new AdditionalCottageService();
        additionalService34.setName("Macevanje");
        additionalService34.setAddPrice(500);
        additionalService34.setPriceList(priceList14);

        HashSet<AdditionalCottageService> serviceList3 = new HashSet<AdditionalCottageService>();
        serviceList3.add(additionalService33);
        serviceList3.add(additionalService34);
        priceList14.setAdditionalServices(serviceList3);

        AvailablePeriodCottage availablePeriodCottage1 = new AvailablePeriodCottage();
        availablePeriodCottage1.setCottage(cottage1);
        availablePeriodCottage1.setFromDate(LocalDateTime.of(2020, 2, 5, 5, 5));
        availablePeriodCottage1.setToDate(LocalDateTime.of(2023, 2, 5, 5, 5));
        Room room1 = new Room();
        room1.setCottage(cottage1);
        room1.setRoomDescription("Soba sa pogledom na nista");
        room1.setNumberOfBeds(2);
        Room room2 = new Room();
        room2.setCottage(cottage1);
        room2.setRoomDescription("Soba sa pogledom na nista");
        room2.setNumberOfBeds(2);
        cottage1.getRooms().add(room1);
        cottage1.getRooms().add(room2);

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
        CottagePriceList priceList15 = new CottagePriceList();
        priceList15.setPrice(6500);
        cottage2.setCottagePriceList(priceList15);
        AvailablePeriodCottage availablePeriodCottage2 = new AvailablePeriodCottage();
        availablePeriodCottage2.setCottage(cottage2);
        availablePeriodCottage2.setFromDate(LocalDateTime.of(2020, 2, 5, 5, 5));
        availablePeriodCottage2.setToDate(LocalDateTime.of(2023, 1, 5, 5, 5));
        Room room3 = new Room();
        room3.setCottage(cottage2);
        room3.setRoomDescription("Soba sa djakuzi kadom");
        room3.setNumberOfBeds(1);
        Room room4 = new Room();
        room4.setCottage(cottage2);
        room4.setRoomDescription("Soba sa montiranim mitraljezom zastava m84 na terasi");
        room4.setNumberOfBeds(5);
        cottage2.getRooms().add(room3);
        cottage2.getRooms().add(room4);

        cottageRepository.save(cottage1);
        availablePeriodCottageRepository.save(availablePeriodCottage1);
        cottageRepository.save(cottage2);
        availablePeriodCottageRepository.save(availablePeriodCottage2);

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
        //adventure1.getPrepaidCustomers().add(prepaidCustomer);
        adventureRepository.save(adventure1);
        //adventureService.prepaidCustomerToAdventure(10, 1);

        //TESTIRANJE OCENJIVANJA INSTRUKTORA
        Grade grade = new Grade();
        grade.setRating(3);
        grade.setRevision("Dobar je ");
        grade.setInstructor(fishingInstructor);
        grade.setEnabled(true);

        Grade grade1 = new Grade();
        grade1.setRating(5);
        grade1.setRevision("Dobar je ");
        grade1.setInstructor(fishingInstructor);
        grade1.setEnabled(true);

        Grade grade2 = new Grade();
        grade2.setRating(5);
        grade2.setRevision("Dobar je ");
        grade2.setInstructor(fishingInstructor);
        grade2.setEnabled(false);

        gradeRepository.save(grade2);
        gradeRepository.save(grade);
        gradeRepository.save(grade1);

        AdventureReservation reservationForEvaluating = new AdventureReservation();
        reservationForEvaluating.setAdventure(adventure);
        reservationForEvaluating.setLength(4);
        reservationForEvaluating.setReservationStart(LocalDateTime.of(2021, 10,15,10,0));
        reservationForEvaluating.setLastDateToReserve(LocalDateTime.of(2021, 10, 15, 15, 0));
        reservationForEvaluating.setCustomer(customer1);
        adventureReservationRepository.save(reservationForEvaluating);

        //TESTIRANJE CUSTOM REZERVACIJE
        AdventureReservation currentReservation = new AdventureReservation();
        currentReservation.setAdventure(adventure);
        currentReservation.setLength(10);
        currentReservation.setReservationStart(LocalDateTime.of(2021, 12,23,10,0));
        currentReservation.setLastDateToReserve(LocalDateTime.of(2021, 10, 15, 15, 0));
        currentReservation.setCustomer(customer1);
        adventureReservationRepository.save(currentReservation);

        //TESTIRANJE REPORTA
        Report reportGood = new Report();
        reportGood.setCustomer(customer1);
        reportGood.setComment("Korisnik je bio korektan");
        reportRepository.save(reportGood);
        Report reportBad = new Report();
        reportBad.setCustomer(customer1);
        reportBad.setBadReport(true);
        reportBad.setComment("Korisnik je nije bio korektan");
        reportRepository.save(reportBad);

        //TESTIRANJE ZAHTEVA ZA BRISANJE
        DeleteProfileRequest deleteProfileRequest = new DeleteProfileRequest();
        deleteProfileRequest.setRequestText("NEKI TEKST");
        deleteProfileRequest.setReviewed(false);
        deleteProfileRequest.setUser(fishingInstructor);
        deleteProfileRequestRepository.save(deleteProfileRequest);

        //TESTIRANJE ZALBE NA AVANTURU
        AdventureReservation reservation23 = new AdventureReservation();
        reservation23.setCustomer(customer1);
        reservation23.setAdventure(adventure);
        reservation23.setLength(4);
        reservation23.setReservationStart(LocalDateTime.of(2021, 9,10,10,20));
        reservation23.setLastDateToReserve(LocalDateTime.of(2021, 10, 10, 15, 0));
        adventureReservationRepository.save(reservation23);

        AdventureComplaint adventureComplaint = new AdventureComplaint();
        adventureComplaint.setText("NEKI TEKST");
        adventureComplaint.setReviewed(false);
        adventureComplaint.setReservation(reservation23);
        adventureComplaintRepository.save(adventureComplaint);

        //TESTIRANJE OBAVESTENJA O PRIHODIMA
        AdventureReservation reservation91 = new AdventureReservation();
        reservation91.setCustomer(customer1);
        reservation91.setAdventure(adventure);
        reservation91.setPrice(1000);
        reservation91.setLength(4);
        reservation91.setLastDateToReserve(LocalDateTime.of(2021, 9,10,10,20));
        reservation91.setReservationStart(LocalDateTime.of(2021, 9,10,10,20));

        AdventureReservation reservation92 = new AdventureReservation();
        reservation92.setCustomer(customer1);
        reservation92.setAdventure(adventure);
        reservation92.setPrice(1000);
        reservation92.setLength(4);
        reservation92.setLastDateToReserve(LocalDateTime.of(2021, 9,10,10,20));
        reservation92.setReservationStart(LocalDateTime.of(2021, 10,10,10,20));

        AdventureReservation reservation93 = new AdventureReservation();
        //reservation93.setCustomer(customer1);
        reservation93.setAdventure(adventure);
        reservation93.setPrice(1000);
        reservation93.setLength(4);
        reservation93.setLastDateToReserve(LocalDateTime.of(2022, 1,9,10,20));
        reservation93.setReservationStart(LocalDateTime.of(2022, 1,12,15,20));

        adventureReservationRepository.save(reservation91);
        adventureReservationRepository.save(reservation92);
        adventureReservationRepository.save(reservation93);
    }

}
