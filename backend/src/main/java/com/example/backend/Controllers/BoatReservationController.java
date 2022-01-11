package com.example.backend.Controllers;

import com.example.backend.Beans.Boat;
import com.example.backend.Beans.BoatReservation;
import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.CottageReservation;
import com.example.backend.Dtos.*;
import com.example.backend.Services.BoatReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("boatReservation")
public class BoatReservationController {
    @Autowired
    private final BoatReservationService boatReservationService;

    public BoatReservationController(BoatReservationService boatReservationService) {
        this.boatReservationService = boatReservationService;
    }

    @GetMapping(path = "/nextActions/{id}")
    public ResponseEntity<Collection<BoatReservation>> getFastCottageReservations(@PathVariable long id){
        return new ResponseEntity<>(boatReservationService.getAllNextActionsOfBoat(id), HttpStatus.OK);
    }

    @GetMapping(path = "/futureCustomerReservation/{customerId}")
    public ResponseEntity<Collection<BoatReservation>> getAllFutureTermsByCustomerId(
            @PathVariable long customerId){
        return new ResponseEntity<>(boatReservationService.getAllFutureTermsByCustomerId(customerId),
                HttpStatus.OK);
    }

    @GetMapping(path = "/pastCustomerReservation/{customerId}")
    public ResponseEntity<Collection<BoatReservation>> getAllPastTermsByCustomerId(
            @PathVariable long customerId){
        return new ResponseEntity<>(boatReservationService.getAllPastTermsByCustomerId(customerId),
                HttpStatus.OK);
    }

    @GetMapping(path = "/pastCustomerReservationWithOutComplaint/{customerId}")
    public ResponseEntity<Collection<BoatReservation>> getAllPastTermsByCustomerIdWithoutComplaint(
            @PathVariable long customerId){
        return new ResponseEntity<>(boatReservationService.getAllPastTermsWithoutComplaintByCustomerId(customerId),
                HttpStatus.OK);
    }

    @PostMapping(path = "/availableBoats/")
    public ResponseEntity<Collection<Boat>> getAllAvailableCottages(@RequestBody ReservationSearchDto search){
        return new ResponseEntity<>(boatReservationService.getAllAvailableBoatsForSearch(search),
                HttpStatus.OK);
    }

    @GetMapping(path = "/ownerReservation/{ownerId}")
    public ResponseEntity<Collection<BoatReservation>> getAllTermsByOwnerId(
            @PathVariable long ownerId){
        return new ResponseEntity<>(boatReservationService.getAllTermsByOwnerId(ownerId),
                HttpStatus.OK);
    }

    @GetMapping(path = "/ownerFastReservation/{ownerId}")
    public ResponseEntity<Collection<BoatReservation>> getAllFastTermsByOwnerId(
            @PathVariable long ownerId){
        return new ResponseEntity<>(boatReservationService.getAllFastTermsByOwnerId(ownerId),
                HttpStatus.OK);
    }

    @GetMapping(path = "/calendar/{ownerId}")
    public ResponseEntity<Collection<CalendarReservationsDto>>
    getAllReservationForCalendarOfOwner(@PathVariable long ownerId){
        return new ResponseEntity<>(boatReservationService.getAllReservationsOfOwnerForCalendar(ownerId),
                HttpStatus.OK);
    }


    @GetMapping(path = "/calendarBoat/{boatId}")
    public ResponseEntity<Collection<CalendarReservationsDto>>
    getAllReservationForCalendarOfBoat(@PathVariable long boatId){
        return new ResponseEntity<>(boatReservationService.getAllReservationsOfBoatForCalendar(boatId),
                HttpStatus.OK);
    }

    @PutMapping(path = "/reserveTerm/")
    public ResponseEntity<BoatReservation> ReserveTerm(@RequestBody CustomerReserveCottageDto reservation) throws InterruptedException {
        BoatReservation boatReservation = boatReservationService.makeNewAppointment(reservation);
        if (boatReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(boatReservation, HttpStatus.OK);
    }

    @PutMapping(path = "/fastReservationPeriod/")
    public ResponseEntity<BoatReservation> FastReservationPeriod(@RequestBody FastReservationDto reservation) throws InterruptedException {
        BoatReservation boatReservation = boatReservationService.makeFastReservationSlot(reservation);
        if (boatReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(boatReservation, HttpStatus.OK);
    }

    @PutMapping(path = "/reserveFastReservation/")
    public ResponseEntity<BoatReservation> ReserveFastReservation(@RequestBody ReservingFastReservationDto reservation) throws InterruptedException {
        BoatReservation boatReservation = boatReservationService.reserveFastReservation(reservation.getUserId(), reservation.getReservationId());
        if (boatReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(boatReservation, HttpStatus.OK);
    }

    @PutMapping(path = "/cancelTerm/")
    public ResponseEntity<BoatReservation> CancelTerm(@RequestBody CancelTermDto cancel){
        BoatReservation boatReservation = boatReservationService.cancelTerm(cancel);
        if (boatReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(boatReservation, HttpStatus.OK);
    }
}
