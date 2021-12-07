package com.example.backend.Controllers;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Services.AdventureReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<AdventureReservation> findAdventureReservationById(@PathVariable long id){
        AdventureReservation adventureReservation = adventureReservationService.findAdventureReservationById(id);
        if(adventureReservation != null)
            return new ResponseEntity<>(adventureReservation, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AdventureReservation> saveAdventureReservation(@RequestBody AdventureReservation
                                                                                     adventureReservation){
        AdventureReservation reservation = adventureReservationService.saveAdventureReservation(adventureReservation);
        if(reservation != null)
            return new ResponseEntity<>(reservation, HttpStatus.CREATED);
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAdventureReservation(@PathVariable long id){
        if(adventureReservationService.deleteAdventureReservation(id))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(path = "/cancel/{id}")
    public ResponseEntity<AdventureReservation> cancelAdventureReservation(@PathVariable long id){
       return new ResponseEntity<>(adventureReservationService.cancelAdventureReservation(id), HttpStatus.OK);
    }

    @PutMapping(path = "/report/{id}/{report}")
    public ResponseEntity<AdventureReservation> addReportToAdventureReservation(@PathVariable long id,
                                                                                @PathVariable String report){
        return new ResponseEntity<>(adventureReservationService.makeReportOfAdventure(id, report), HttpStatus.OK);
    }
}
