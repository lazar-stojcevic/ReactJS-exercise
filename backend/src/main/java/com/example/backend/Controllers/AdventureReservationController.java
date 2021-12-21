package com.example.backend.Controllers;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Dtos.CustomerReserveTermDto;
import com.example.backend.Dtos.MakeFastReservationDto;
import com.example.backend.Dtos.ReservationSearchDto;
import com.example.backend.Dtos.ReserveAdventureDto;
import com.example.backend.Services.AdventureReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("adventureReservation")
public class AdventureReservationController {
    @Autowired
    private final AdventureReservationService adventureReservationService;

    public AdventureReservationController(AdventureReservationService adventureReservationService){
        this.adventureReservationService = adventureReservationService;
    }

    @GetMapping
    public ResponseEntity<Collection<AdventureReservation>> getAllAdventureReservations(){
        return new ResponseEntity<>(adventureReservationService.getAllAdventureReservations(), HttpStatus.OK);
    }

    @GetMapping(path = "/nextReservations/{adventureId}")
    public ResponseEntity<Collection<AdventureReservation>> getAllNextReservedTermsOfAdventure(
            @PathVariable long adventureId){
        return new ResponseEntity<>(adventureReservationService.getAllNextReservedTermsOfAdventure(adventureId),
                HttpStatus.OK);
    }

    @GetMapping(path = "/term/{termId}")
    public ResponseEntity<AdventureReservation> getAdventureReservation(
            @PathVariable long termId){
        return new ResponseEntity<>(adventureReservationService.getAdventureReservationById(termId),
                HttpStatus.OK);
    }

    @PutMapping(path = "/reserveTerm/")
    public ResponseEntity<AdventureReservation> ReserveTerm(@RequestBody CustomerReserveTermDto reservation){
        AdventureReservation adventureReservation = adventureReservationService.makeNewAppointment(reservation);
        if (adventureReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(adventureReservation, HttpStatus.OK);
    }

    @PostMapping(path = "/availableAdventures/")
    public ResponseEntity<Collection<AdventureReservation>> getAllAvailableAdventures(@RequestBody ReservationSearchDto search){
        return new ResponseEntity<>(adventureReservationService.getAllAvailableReservationsForSearch(search),
                HttpStatus.OK);
    }

    @GetMapping(path = "/fast/{adventureId}")
    public ResponseEntity<Collection<AdventureReservation>> getAllFreeFastReservationsOfAdventure(
            @PathVariable long adventureId){
        return new ResponseEntity<>(adventureReservationService.getAllFreeFastReservations(adventureId),
                HttpStatus.OK);
    }

    @GetMapping(path = "/pastReservation/{instructorId}")
    public ResponseEntity<Collection<AdventureReservation>> getAllPastReservationOfInstructor(
            @PathVariable long instructorId){
        return new ResponseEntity<>(adventureReservationService.getAllPastReservationOfInstructor(instructorId),
                HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AdventureReservation> findAdventureReservationById(@PathVariable long id) {
        AdventureReservation adventureReservation = adventureReservationService.findAdventureReservationById(id);
        if (adventureReservation != null)
            return new ResponseEntity<>(adventureReservation, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/makeFastReservation")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<AdventureReservation> makeFastReservation(@RequestBody MakeFastReservationDto dto){
        AdventureReservation adventureReservation = adventureReservationService.createFreeFastReservation(dto);
        if(adventureReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(adventureReservation, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAdventureReservation(@PathVariable long id){
        if(adventureReservationService.deleteAdventureReservation(id))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/cancel/{id}")
    public ResponseEntity<AdventureReservation> cancelAdventureReservation(@PathVariable long id){
        AdventureReservation adventureReservation = adventureReservationService.cancelAdventureReservation(id);
        if(adventureReservation == null)
            return new ResponseEntity<>(new AdventureReservation(), HttpStatus.NOT_ACCEPTABLE);
       return new ResponseEntity<>(adventureReservationService.cancelAdventureReservation(id), HttpStatus.OK);
    }

    @PutMapping(path = "/report/{id}/{report}")
    public ResponseEntity<AdventureReservation> addReportToAdventureReservation(@PathVariable long id,
                                                                                @PathVariable String report){
        return new ResponseEntity<>(adventureReservationService.makeReportOfAdventureReservation(id, report), HttpStatus.OK);
    }

    @PutMapping(path = "/fastReserve")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<AdventureReservation> fastReserve(@RequestBody ReserveAdventureDto dto){
        AdventureReservation reservation = adventureReservationService.fastReserveAdventure(dto);
        if(reservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }
}
