package com.example.backend.service;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.ReservationSearchDto;
import com.example.backend.Repository.AdventureReservationRepository;
import com.example.backend.Services.AdventureReservationService;
import com.example.backend.Services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@SpringBootTest
public class AdventureReservationServiceTests {
    @Mock
    AdventureReservationRepository adventureReservationRepositoryMock;

    @Mock
    CustomerService customerServiceMock;

    private AdventureReservationService subject;

    @BeforeEach
    public void setUp(){
        subject = new AdventureReservationService(
                adventureReservationRepositoryMock,
                customerServiceMock);
    }

    // Student 1
    @Test
    public void testGetAllAvailableReservationsForSearch() {
        //given
        AdventureReservation reservation1 = new AdventureReservation();
        Address address1 = new Address();
        address1.setCountry("Srbija"); address1.setStreet("ulica 1"); address1.setCity("grad 1");
        Adventure adventure1 = new Adventure();
        adventure1.setMaxPersons(10);
        adventure1.setAddress(address1);
        reservation1.setAdventure(adventure1);
        reservation1.setForbidenCustomers(new ArrayList<>());
        reservation1.setLastDateToReserve(LocalDateTime.now().plusDays(8));

        AdventureReservation reservation2 = new AdventureReservation();
        Address address2 = new Address();
        address2.setCountry("Srbija"); address2.setStreet("ulica 2"); address2.setCity("grad 2");
        Adventure adventure2 = new Adventure();
        adventure2.setMaxPersons(15);
        adventure2.setAddress(address2);
        reservation2.setAdventure(adventure2);
        reservation2.setForbidenCustomers(new ArrayList<Customer>());
        reservation2.setLastDateToReserve(LocalDateTime.now().plusDays(10));

        ReservationSearchDto searchDto = new ReservationSearchDto();
        searchDto.setDateFrom(LocalDateTime.now().plusDays(10));
        searchDto.setDateTo(LocalDateTime.now().plusDays(100));
        searchDto.setId(1L);
        searchDto.setCity("grad");
        searchDto.setCountry("Srbija");
        searchDto.setCaptain(false);
        searchDto.setPersons(5);

        ArrayList<AdventureReservation> retVal = new ArrayList<>();
            retVal.add(reservation1);
            retVal.add(reservation2);

        Customer customer1 = new Customer();
        customer1.setFirstname("Testic");

        //when
        Mockito.when(adventureReservationRepositoryMock.findByreservationStartBetween(any(), any())).thenReturn(retVal);
        Mockito.when(customerServiceMock.findCustomerById(1L)).thenReturn(customer1);
        //then
        assertThat(subject.getAllAvailableReservationsForSearch(searchDto).size()).isEqualTo(2);
    }

    //Student 1
    @Test
    public void unsuccessfulCancelReservationTest(){
        //given
        AdventureReservation reservation1 = new AdventureReservation();
        reservation1.setId(1);
        Address address1 = new Address();
        address1.setCountry("Srbija"); address1.setStreet("ulica 1"); address1.setCity("grad 1");
        Adventure adventure1 = new Adventure();
        adventure1.setMaxPersons(10);
        adventure1.setAddress(address1);
        reservation1.setAdventure(adventure1);
        reservation1.setForbidenCustomers(new ArrayList<>());
        reservation1.setLastDateToReserve(LocalDateTime.now().plusDays(8));
        reservation1.setReservationStart(LocalDateTime.now().plusDays(2));
        //when
        Mockito.when(adventureReservationRepositoryMock.findById(anyLong())).thenReturn(java.util.Optional.of(reservation1));
        //then
        assertThat(subject.cancelAdventureReservation(1)).isNull();
    }

    //Student 1
    @Test
    public void testGetAllNextFreeActionsOfInstructor(){
        //given
        FishingInstructor instructor = new FishingInstructor();
        instructor.setId(10);
        FishingInstructor instructor2 = new FishingInstructor();
        instructor2.setId(11);

        AdventureReservation reservation1 = new AdventureReservation();
        Address address1 = new Address();
        address1.setCountry("Srbija"); address1.setStreet("ulica 1"); address1.setCity("grad 1");
        Adventure adventure1 = new Adventure();
        adventure1.setInstructor(instructor);
        adventure1.setMaxPersons(10);
        adventure1.setAddress(address1);
        reservation1.setAdventure(adventure1);
        reservation1.setForbidenCustomers(new ArrayList<>());
        reservation1.setLastDateToReserve(LocalDateTime.now().plusDays(8));
        reservation1.setReservationStart(LocalDateTime.now().plusDays(15));
        reservation1.setDiscount(10);

        AdventureReservation reservation2 = new AdventureReservation();
        Address address2 = new Address();
        address2.setCountry("Srbija"); address2.setStreet("ulica 2"); address2.setCity("grad 2");
        Adventure adventure2 = new Adventure();
        adventure2.setInstructor(instructor2);
        adventure2.setMaxPersons(15);
        adventure2.setAddress(address2);
        reservation2.setAdventure(adventure2);
        reservation2.setForbidenCustomers(new ArrayList<Customer>());
        reservation2.setLastDateToReserve(LocalDateTime.now().plusDays(10));
        reservation2.setReservationStart(LocalDateTime.now().plusDays(21));
        reservation2.setDiscount(10);

        ArrayList<AdventureReservation> retVal = new ArrayList<>();
        retVal.add(reservation1);
        retVal.add(reservation2);
        //when
        Mockito.when(adventureReservationRepositoryMock.findAll()).thenReturn(retVal);
        //then
        assertThat(subject.getAllNextFreeActionsOfInstructor(10).size()).isEqualTo(1);

    }


}
