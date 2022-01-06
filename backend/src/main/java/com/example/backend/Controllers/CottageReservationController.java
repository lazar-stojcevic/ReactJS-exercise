package com.example.backend.Controllers;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.CottageReservation;
import com.example.backend.Dtos.CustomerReserveCottageDto;
import com.example.backend.Dtos.CustomerReserveTermDto;
import com.example.backend.Dtos.ReservationSearchDto;
import com.example.backend.Services.CottageReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("cottageReservation")
public class CottageReservationController {
    @Autowired
    private final CottageReservationService cottageReservationService;

    public CottageReservationController(CottageReservationService cottageReservationService) {
        this.cottageReservationService = cottageReservationService;
    }

    @GetMapping
    public ResponseEntity<Collection<CottageReservation>> getAllCottageReservations(){
        return new ResponseEntity<>(cottageReservationService.getAllCottageReservations(), HttpStatus.OK);
    }

    @PostMapping(path = "/availableCottages/")
    public ResponseEntity<Collection<Cottage>> getAllAvailableCottages(@RequestBody ReservationSearchDto search){
        return new ResponseEntity<>(cottageReservationService.getAllAvailableCottagesForSearch(search),
                HttpStatus.OK);
    }

    @PutMapping(path = "/reserveTerm/")
    public ResponseEntity<CottageReservation> ReserveTerm(@RequestBody CustomerReserveCottageDto reservation) throws InterruptedException {
        CottageReservation cottageReservation = cottageReservationService.makeNewAppointment(reservation);
        if (cottageReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(cottageReservation, HttpStatus.OK);
    }
}
