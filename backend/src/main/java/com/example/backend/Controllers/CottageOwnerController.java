package com.example.backend.Controllers;

import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.CottageOwner;
import com.example.backend.Beans.Customer;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Dtos.CottageOwnerChangeDto;
import com.example.backend.Dtos.FishingInstructorChangeDto;
import com.example.backend.Dtos.PasswordChangeDto;
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
@RequestMapping(path = "cottageOwner")
public class CottageOwnerController {
    @Autowired
    private CottageOwnerService cottageOwnerService;

    public CottageOwnerController(CottageOwnerService cottageOwnerService){this.cottageOwnerService = cottageOwnerService;}

    @GetMapping
    public ResponseEntity<Collection<CottageOwner>> getAll(){
        return new ResponseEntity<>(cottageOwnerService.getAllCottageOwners(), HttpStatus.OK);
    }

    @GetMapping (path = "/{id}")
    public ResponseEntity<CottageOwner> getCustomerById(@PathVariable long id){
        return new ResponseEntity<>(cottageOwnerService.findCottageOwner(id), HttpStatus.OK);
    }
    @GetMapping (path = "/cottages/{id}")
    public ResponseEntity<Set<Cottage>> getCottageById(@PathVariable long id){
        return new ResponseEntity<>(cottageOwnerService.findCottageOwner(id).getCottage(), HttpStatus.OK);
    }

    @PutMapping(path = "/password", consumes = "application/json")
    @PreAuthorize("hasRole('COTTAGE_OWNER')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto passwordChangeDto){
        cottageOwnerService.changePassword(passwordChangeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<CottageOwner> updateCottageOwner(
            @RequestBody CottageOwnerChangeDto cottageOwnerChangeDto){
        return new ResponseEntity<>(cottageOwnerService.updateCottageOwner(cottageOwnerChangeDto), HttpStatus.CREATED);
    }
}
