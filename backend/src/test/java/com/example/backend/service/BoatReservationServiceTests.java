package com.example.backend.service;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.ReservationSearchDto;
import com.example.backend.Repository.AdventureReservationRepository;
import com.example.backend.Repository.BoatReservationRepository;
import com.example.backend.Repository.ForbiddenCustomerToBoatRepository;
import com.example.backend.Services.AdventureReservationService;
import com.example.backend.Services.BoatReservationService;
import com.example.backend.Services.BoatService;
import com.example.backend.Services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class BoatReservationServiceTests {
    @Mock
    BoatReservationRepository boatReservationRepositoryMock;

    @Mock
    ForbiddenCustomerToBoatRepository forbiddenCustomerToBoatRepositoryMock;

    @Mock
    CustomerService customerServiceMock;

    @Mock
    BoatService boatServiceMock;


    private BoatReservationService subject;

    @BeforeEach
    public void setUp(){
        subject = new BoatReservationService(
                boatReservationRepositoryMock, forbiddenCustomerToBoatRepositoryMock);
    }

    //Student2
    @Test
    public void testBadFastReservationRequest() {

        BoatReservation reservation1 = new BoatReservation();
        Address address1 = new Address();
        address1.setCountry("Srbija"); address1.setStreet("ulica 1"); address1.setCity("grad 1");
        Boat boat = new Boat();
        reservation1.setBoat(boat);
        reservation1.setReservationStart(LocalDateTime.now().plusDays(2));
        reservation1.setReservationEnd(LocalDateTime.now().plusDays(2));
        reservation1.setFast(true);

        Customer customer1 = new Customer();
        customer1.setFirstname("Marko");
        reservation1.setCustomer(customer1);


        Mockito.when(boatReservationRepositoryMock.findById(any())).thenReturn(java.util.Optional.of(reservation1));
        Mockito.when(customerServiceMock.findCustomerById(1L)).thenReturn(customer1);

        assertThat(subject.reserveFastReservation(1, 2)).isNull();
    }

    //Student2
    @Test
    public void testGetAllReservationsForCalendar() {

        BoatReservation reservation1 = new BoatReservation();
        Boat boat = new Boat();
        reservation1.setBoat(boat);
        reservation1.setReservationStart(LocalDateTime.now().plusDays(2));
        reservation1.setReservationEnd(LocalDateTime.now().plusDays(2));
        reservation1.setFast(true);

        BoatReservation reservation2 = new BoatReservation();
        Boat boat1 = new Boat();
        reservation2.setBoat(boat1);
        reservation2.setReservationStart(LocalDateTime.now().plusDays(2));
        reservation2.setReservationEnd(LocalDateTime.now().plusDays(2));
        reservation2.setFast(true);

        ArrayList<BoatReservation> retVal = new ArrayList<>();
        retVal.add(reservation1);
        retVal.add(reservation2);


        Mockito.when(boatReservationRepositoryMock.getAllReservationsOfOwnerForCalendar(1L)).thenReturn(retVal);

        assertThat(subject.getAllReservationsOfOwnerForCalendar(1).size()).isEqualTo(2);
    }


}
