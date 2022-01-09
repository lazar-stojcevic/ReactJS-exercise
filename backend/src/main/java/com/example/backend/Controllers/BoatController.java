package com.example.backend.Controllers;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.*;
import com.example.backend.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("boat")
public class BoatController {

    private final BoatService boatService;

    @Autowired
    private AdditionalBoatServiceService additionalBoatServiceService;

    @Autowired
    private AvailablePeriodsBoatService availablePeriodsBoatService;

    public BoatController(BoatService boatService){ this.boatService = boatService;}

    @GetMapping("/getAll")
    public ResponseEntity<Collection<Boat>> getAll(){
        return new ResponseEntity<>(this.boatService.findAllBoats(), HttpStatus.OK);
    }


    @GetMapping(path = "/getOne/{id}")
    public ResponseEntity<Boat> getCottageById(@PathVariable long id){
        Boat boat = boatService.findById(id);
        if (boat != null)
            return new ResponseEntity<>(boat, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/additionalServices/{boatId}")
    public ResponseEntity<Collection<AdditionalBoatService>> getAdditionalServicesOfBoat(
            @PathVariable long boatId){
        return new ResponseEntity<>(boatService.findById(boatId).getPriceList().getAdditionalServices(),
                HttpStatus.OK);
    }

    @GetMapping(path = "/availablePeriod/{boatId}")
    public ResponseEntity<Collection<AvailablePeriodBoat>> getAvailablePeriodOfBoat(
            @PathVariable long boatId){
        return new ResponseEntity<>(boatService.findById(boatId).getPeriods(),
                HttpStatus.OK);
    }

   @GetMapping (path = "isSubcribed/{cottageId}/{userId}")
    public ResponseEntity<Boolean> isCustomerSubscribedToCottage(@PathVariable long boatId, @PathVariable long userId){

        return new ResponseEntity<>(boatService.isUserSubcribed(boatId, userId), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public Boat save(@RequestBody BoatDto boatDto){
        return this.boatService.saveBoat(boatDto);
    }

    @PutMapping(consumes = "application/json")
    public Boat update(@RequestBody BoatDto boatDto){

        return this.boatService.updateBoat(boatDto);
    }

    @PutMapping(path = "availablePeriod/")
    public AvailablePeriodBoat addAvailablePeriod(@RequestBody AvailablePeriodDto periodDto){
        return this.availablePeriodsBoatService.createBoatAvailablePeriod(periodDto);
    }

    @PutMapping(path = "/subscribe", consumes = "application/json")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> subscribe(@RequestBody NewSubcriptionDto newSubcriptionDto){
        try {
            boatService.newSubscription(newSubcriptionDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/unsubscribe", consumes = "application/json")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> unsubscribe(@RequestBody NewSubcriptionDto forUnsubscribe){
        try {
            boatService.unsubscribe(forUnsubscribe);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        boatService.deleteBoat(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "additionalService/{id}")
    public ResponseEntity<?> deleteAddService(@PathVariable long id){
        additionalBoatServiceService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "additionalService/")
    public Boat addAdditionalService(@RequestBody AdditionalServiceDto serviceDto){
        return this.boatService.addAdditionalService(serviceDto);
    }

}
