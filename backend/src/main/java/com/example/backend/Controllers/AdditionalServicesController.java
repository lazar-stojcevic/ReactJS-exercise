package com.example.backend.Controllers;

import com.example.backend.Beans.AdditionalServices;
import com.example.backend.Services.AdditionalServicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping(path = "additionalServices")
public class AdditionalServicesController {
    @Autowired
    private final AdditionalServicesService additionalServicesService;

    public AdditionalServicesController(AdditionalServicesService additionalServicesService){
        this.additionalServicesService = additionalServicesService;
    }

    @PostMapping(path = "/{adventureId}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<AdditionalServices> addAdditionalServices(
            @PathVariable long adventureId, @RequestBody AdditionalServices additionalServices){
        return new ResponseEntity<>(additionalServicesService.addAdditionalServices(
                adventureId, additionalServices), HttpStatus.CREATED);
    }
}
