package com.example.backend.Controllers;

import com.example.backend.Beans.AdditionalService;
import com.example.backend.Beans.Adventure;
import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.Image;
import com.example.backend.Services.AdventureReservationService;
import com.example.backend.Services.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("adventure")
public class AdventureController {
    @Autowired
    private final AdventureService adventureService;

    @Autowired
    private final AdventureReservationService adventureReservationService;

    public AdventureController(AdventureService adventureService, AdventureReservationService adventureReservationService){
        this.adventureService = adventureService;
        this.adventureReservationService = adventureReservationService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Adventure>> getAllAdventure(){
        return new ResponseEntity<>(adventureService.getAllAdventures(), HttpStatus.OK);
    }

    @GetMapping(path = "/additionalServices/{reservationId}")
    public ResponseEntity<Collection<AdditionalService>> getAdditionalServicesOfAdventure(
            @PathVariable long reservationId){
        return new ResponseEntity<>(adventureReservationService.getAdventureReservationById(reservationId).getAdventure().getPriceList().getAdditionalServices(),
                HttpStatus.OK);
    }

    @GetMapping(path="/images/{adventureId}")
    public ResponseEntity<List<Image>> getAllImagesOfAdventure(@PathVariable long adventureId){
        return new ResponseEntity<>(adventureService.getAllImagesOfAdventure(adventureId), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Adventure> getAdventureById(@PathVariable long id){
        Adventure adventure = adventureService.findAdventureById(id);
        if(adventure != null)
            return new ResponseEntity<>(adventure, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/instructor/{instructorId}")
    public ResponseEntity<Collection<Adventure>> getAllAdventuresOfInstructor(@PathVariable long instructorId){
        return new ResponseEntity<>(adventureService.getAllAdventuresOfInstructor(instructorId), HttpStatus.OK);
    }

    @PostMapping(path = "/{instructorId}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Adventure> saveAdventure(@PathVariable long instructorId,
                                                   @RequestBody Adventure adventure){
        return new ResponseEntity<>(adventureService.saveAdventure(instructorId, adventure), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<?> deleteAdventure(@PathVariable long id){
        if(adventureService.canAdventureBeDeleted(id))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Adventure> updateAdventure(@RequestBody Adventure adventure){
        return new ResponseEntity<>(adventureService.updateAdventure(adventure), HttpStatus.OK);
    }

    @PutMapping(path = "/prepaid/{customerId}/{adventureId}")
    public ResponseEntity<Adventure> prepaidCustomerToAdventure(
            @PathVariable long customerId, @PathVariable long adventureId){
        return new ResponseEntity<>(adventureService.prepaidCustomerToAdventure(customerId, adventureId), HttpStatus.OK);
    }
}
