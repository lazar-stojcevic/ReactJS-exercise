package com.example.backend.service;

import com.example.backend.Beans.*;
import com.example.backend.Repository.*;
import com.example.backend.Services.BoatReservationService;
import com.example.backend.Services.CottageReservationService;
import com.example.backend.Services.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class CottageReservationServiceTest {
    @Mock
    CottageReservationRepository cottageReservationRepositoryMock;

    @Mock
    ForbiddenCustomerToCottageRepository forbiddenCustomerToCottageRepositoryMock;

    @Mock
    CottageRepository cottageRepositoryMock;

    @Mock
    CustomerService customerServiceMock;


    private CottageReservationService subject;

    @BeforeEach
    public void setUp(){
        subject = new CottageReservationService(
                cottageReservationRepositoryMock,cottageRepositoryMock, forbiddenCustomerToCottageRepositoryMock);
    }

    //Student2
    @Test
    public void testBadFastReservationRequest() {

        CottageReservation reservation1 = new CottageReservation();
        Address address1 = new Address();
        address1.setCountry("Srbija"); address1.setStreet("ulica 1"); address1.setCity("grad 1");
        Cottage cottage = new Cottage();
        reservation1.setCottage(cottage);
        reservation1.setReservationStart(LocalDateTime.now().plusDays(2));
        reservation1.setReservationEnd(LocalDateTime.now().plusDays(2));
        reservation1.setFast(true);

        Customer customer1 = new Customer();
        customer1.setFirstname("Marko");
        reservation1.setCustomer(customer1);


        Mockito.when(cottageReservationRepositoryMock.findById(any())).thenReturn(java.util.Optional.of(reservation1));
        Mockito.when(customerServiceMock.findCustomerById(1L)).thenReturn(customer1);

        assertThat(subject.reserveFastReservation(1, 2)).isNull();
    }
}
