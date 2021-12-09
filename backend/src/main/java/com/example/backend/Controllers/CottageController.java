package com.example.backend.Controllers;

import com.example.backend.Beans.Adventure;
import com.example.backend.Beans.Cottage;
import com.example.backend.Dtos.CottageDto;
import com.example.backend.Services.AdminService;
import com.example.backend.Services.CottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping
    public Collection<Cottage> getAll(){
        return this.cottageService.findAllCottages();
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Cottage> getCottageById(@PathVariable long id){
        Cottage cottage = cottageService.findById(id);
        if (cottage != null)
            return new ResponseEntity<>(cottage, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    public Cottage save(@RequestBody CottageDto cottage){
        return this.cottageService.saveCottage(cottage);
    }
}
