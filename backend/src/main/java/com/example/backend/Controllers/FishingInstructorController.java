package com.example.backend.Controllers;

import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Services.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "fishingInstructor")
public class FishingInstructorController {

    private final FishingInstructorService fishingInstructorService;

    @Autowired
    public FishingInstructorController(FishingInstructorService fishingInstructorService){
        this.fishingInstructorService = fishingInstructorService;
    }

    @GetMapping
    public Collection<FishingInstructor> getAll(){
        return fishingInstructorService.getAll();
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<FishingInstructor> save(@RequestBody FishingInstructor fishingInstructor){
        FishingInstructor instructor = this.fishingInstructorService.save(fishingInstructor);
        if(instructor != null)
            return new ResponseEntity<>(instructor, HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
