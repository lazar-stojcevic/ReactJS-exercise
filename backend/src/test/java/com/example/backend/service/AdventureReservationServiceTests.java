package com.example.backend.service;

import com.example.backend.Beans.Address;
import com.example.backend.Beans.Adventure;
import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.Customer;
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

        Mockito.when(adventureReservationRepositoryMock.findByreservationStartBetween(any(), any())).thenReturn(retVal);
        Mockito.when(customerServiceMock.findCustomerById(1L)).thenReturn(customer1);

        assertThat(subject.getAllAvailableReservationsForSearch(searchDto).size()).isEqualTo(2);
    }
}
