package com.example.backend.service;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.ComplaintForReviewDto;
import com.example.backend.Repository.AdventureReservationRepository;
import com.example.backend.Repository.BoatReservationRepository;
import com.example.backend.Repository.ComplaintRepository;
import com.example.backend.Repository.CottageReservationRepository;
import com.example.backend.Services.ComplaintService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepositoryMock;
    @Mock
    private AdventureReservationRepository adventureReservationRepositoryMock;
    @Mock
    private BoatReservationRepository boatReservationRepositoryMock;
    @Mock
    private CottageReservationRepository cottageReservationRepositoryMock;

    @InjectMocks
    private ComplaintService complaintService;

    @Test
    public void testFindAll(){
        when(complaintRepositoryMock.findAll()).thenReturn(Arrays.asList(new Complaint(1, "A", false),
                new Complaint(2, "B", true)));
        Collection<Complaint> complaintList =  complaintService.findAll();

        assertEquals(complaintList.size(), 2);
    }

    @Test
    public void testFindAllNotReviewedComplaints(){
        when(adventureReservationRepositoryMock.getAllNotReviewedAdventureComplaint()).thenReturn(List.of(makeAdventureReservation()));
        when(boatReservationRepositoryMock.getAllNotReviewedBoatComplaint()).thenReturn(List.of(makeBoatReservation()));
        when(cottageReservationRepositoryMock.getAllNotReviewedCottageComplaint()).thenReturn(List.of(makeCottageReservation()));

        List<ComplaintForReviewDto> list = (List<ComplaintForReviewDto>) complaintService.getAllNotReviewedComplaint();

        assertEquals(list.size(), 3);
    }

    private AdventureReservation makeAdventureReservation(){
        AdventureReservation adventureReservation = new AdventureReservation();
        adventureReservation.setComplaint(new Complaint(1, "A", false));
        FishingInstructor fishingInstructor = new FishingInstructor();
        fishingInstructor.setEmail("neki@mejl");
        Adventure adventure = new Adventure();
        adventure.setInstructor(fishingInstructor);
        Customer customer = new Customer();
        customer.setEmail("neki@drugi");
        adventureReservation.setAdventure(adventure);
        adventureReservation.setCustomer(customer);
        return adventureReservation;
    }

    private CottageReservation makeCottageReservation(){
        CottageReservation reservation = new CottageReservation();
        reservation.setComplaint(new Complaint(2, "C", false));
        CottageOwner cottageOwner = new CottageOwner();
        cottageOwner.setEmail("cott@m");
        Cottage cottage = new Cottage();
        cottage.setCottageOwner(cottageOwner);
        Customer customer = new Customer();
        customer.setEmail("cccc@mam");
        reservation.setCustomer(customer);
        reservation.setCottage(cottage);
        return reservation;
    }

    private BoatReservation makeBoatReservation(){
        BoatReservation reservation = new BoatReservation();
        reservation.setComplaint(new Complaint(3, "B", false));
        BoatOwner boatOwner = new BoatOwner();
        boatOwner.setEmail("bo@majl");
        Customer customer = new Customer();
        customer.setEmail("cub@mjl");
        Boat boat = new Boat();
        boat.setBoatOwner(boatOwner);
        reservation.setBoat(boat);
        reservation.setCustomer(customer);
        return reservation;
    }
}
