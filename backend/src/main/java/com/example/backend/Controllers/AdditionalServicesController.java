package com.example.backend.Controllers;

import com.example.backend.Beans.AdditionalService;
import com.example.backend.Services.AdditionalServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping(path = "additionalServices")
public class AdditionalServicesController {
    @Autowired
    private final AdditionalServiceService additionalServicesService;

    public AdditionalServicesController(AdditionalServiceService additionalServicesService){
        this.additionalServicesService = additionalServicesService;
    }

    @PostMapping(path = "/{adventureId}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<AdditionalService> addAdditionalServices(
            @PathVariable long adventureId, @RequestBody AdditionalService additionalServices){
        return new ResponseEntity<>(additionalServicesService.addAdditionalServices(
                adventureId, additionalServices), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{adventureId}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Collection<AdditionalService>> getAllAdditionalServicesOfAdventure(
            @PathVariable long adventureId){
        return new ResponseEntity<>(additionalServicesService.getAllAdditionalServicesOfAdventure(adventureId),
                HttpStatus.OK);
    }
}
