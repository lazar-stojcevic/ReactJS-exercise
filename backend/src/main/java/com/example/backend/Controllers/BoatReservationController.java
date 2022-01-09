package com.example.backend.Controllers;

import com.example.backend.Beans.Boat;
import com.example.backend.Beans.BoatReservation;
import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.CottageReservation;
import com.example.backend.Dtos.CustomerReserveCottageDto;
import com.example.backend.Dtos.ReservationSearchDto;
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


    @PostMapping(path = "/availableBoats/")
    public ResponseEntity<Collection<Boat>> getAllAvailableCottages(@RequestBody ReservationSearchDto search){
        return new ResponseEntity<>(boatReservationService.getAllAvailableBoatsForSearch(search),
                HttpStatus.OK);
    }

    @PutMapping(path = "/reserveTerm/")
    public ResponseEntity<BoatReservation> ReserveTerm(@RequestBody CustomerReserveCottageDto reservation) throws InterruptedException {
        BoatReservation boatReservation = boatReservationService.makeNewAppointment(reservation);
        if (boatReservation == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(boatReservation, HttpStatus.OK);
    }
}
