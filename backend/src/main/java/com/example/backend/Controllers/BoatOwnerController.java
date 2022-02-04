package com.example.backend.Controllers;

import com.example.backend.Beans.Boat;
import com.example.backend.Beans.BoatOwner;
import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.CottageOwner;
import com.example.backend.Dtos.BoatOwnerChangeDto;
import com.example.backend.Dtos.CottageOwnerChangeDto;
import com.example.backend.Dtos.PasswordChangeDto;
import com.example.backend.Services.BoatOwnerService;
import com.example.backend.Services.CottageOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Set;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping(path = "boatOwner")
public class BoatOwnerController {
    @Autowired
    private BoatOwnerService boatOwnerService;

    public BoatOwnerController(BoatOwnerService boatOwnerService){this.boatOwnerService = boatOwnerService;}

    @GetMapping
    public ResponseEntity<Collection<BoatOwner>> getAll(){
        return new ResponseEntity<>(boatOwnerService.getAllBoatOwners(), HttpStatus.OK);
    }

    @GetMapping (path = "/{id}")
    public ResponseEntity<BoatOwner> getCustomerById(@PathVariable long id){
        return new ResponseEntity<>(boatOwnerService.findBoatOwner(id), HttpStatus.OK);
    }
    @GetMapping (path = "/boats/{id}")
    public ResponseEntity<Set<Boat>> getCottageById(@PathVariable long id){
        return new ResponseEntity<>(boatOwnerService.findBoatOwner(id).getBoats(), HttpStatus.OK);
    }

    @PutMapping(path = "/password", consumes = "application/json")
    @PreAuthorize("hasRole('BOAT_OWNER')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto passwordChangeDto){
        boatOwnerService.changePassword(passwordChangeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<BoatOwner> updateCottageOwner(
            @RequestBody BoatOwnerChangeDto boatOwnerChangeDto){
        return new ResponseEntity<>(boatOwnerService.updateBoatOwner(boatOwnerChangeDto), HttpStatus.CREATED);
    }
}
