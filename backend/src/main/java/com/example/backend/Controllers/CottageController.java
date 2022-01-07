package com.example.backend.Controllers;

import com.example.backend.Beans.AdditionalCottageService;
import com.example.backend.Beans.AdditionalService;
import com.example.backend.Beans.Adventure;
import com.example.backend.Beans.Cottage;
import com.example.backend.Dtos.CottageDto;
import com.example.backend.Dtos.NewSubcriptionDto;
import com.example.backend.Services.AdminService;
import com.example.backend.Services.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("cottage")
public class CottageController {
    private final CottageService cottageService;

    @Autowired
    public CottageController(CottageService cottageService){
        this.cottageService = cottageService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Collection<Cottage>> getAll(){
        return new ResponseEntity<>(this.cottageService.findAllCottages(), HttpStatus.OK);
    }


    @GetMapping(path = "{id}")
    public ResponseEntity<Cottage> getCottageById(@PathVariable long id){
        Cottage cottage = cottageService.findById(id);
        if (cottage != null)
            return new ResponseEntity<>(cottage, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/additionalServices/{cottageId}")
    public ResponseEntity<Collection<AdditionalCottageService>> getAdditionalServicesOfAdventure(
            @PathVariable long cottageId){
        return new ResponseEntity<>(cottageService.findById(cottageId).getCottagePriceList().getAdditionalServices(),
                HttpStatus.OK);
    }

    @GetMapping (path = "isSubcribed/{cottageId}/{userId}")
    public ResponseEntity<Boolean> isCustomerSubscribedToCottage(@PathVariable long cottageId, @PathVariable long userId){

        return new ResponseEntity<>(cottageService.isUserSubcribed(cottageId, userId), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public Cottage save(@RequestBody CottageDto cottage){
        return this.cottageService.saveCottage(cottage);
    }

    @PutMapping(consumes = "application/json")
    public Cottage update(@RequestBody CottageDto cottage){

        return this.cottageService.updateCottage(cottage);
    }

    @PutMapping(path = "/subscribe", consumes = "application/json")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> subscribe(@RequestBody NewSubcriptionDto newSubcriptionDto){
        try {
            cottageService.newSubscription(newSubcriptionDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/unsubscribe", consumes = "application/json")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> unsubscribe(@RequestBody NewSubcriptionDto forUnsubscribe){
        try {
            cottageService.unsubscribe(forUnsubscribe);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        cottageService.deleteCottage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
