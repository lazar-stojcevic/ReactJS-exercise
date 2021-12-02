package com.example.backend.Controllers;

import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Beans.HolidayTimespan;
import com.example.backend.Dtos.PasswordChangeDto;
import com.example.backend.Services.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "fishingInstructor")
public class FishingInstructorController {

    @Autowired
    private final FishingInstructorService fishingInstructorService;

    public FishingInstructorController(FishingInstructorService fishingInstructorService){
        this.fishingInstructorService = fishingInstructorService;
    }

    @GetMapping
    public ResponseEntity<Collection<FishingInstructor>> getAll(){
        return new ResponseEntity<>(fishingInstructorService.getAllFishingInstructors(), HttpStatus.OK);
    }

    @GetMapping (path = "/{id}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<FishingInstructor> getInstructorById(@PathVariable long id){
        return new ResponseEntity<>(fishingInstructorService.findFishingInstructorById(id), HttpStatus.OK);
    }
    @PutMapping(path = "/enable/{id}")
    public ResponseEntity<?> enableFishingInstructor(@PathVariable long id){
        FishingInstructor instructor = fishingInstructorService.enableFishingInstructor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO: AKO TREBA I NEKO POSEBNO OBRAZLOZENJE DA SE DODA?
    @DeleteMapping(path = "/disable/{id}")
    public ResponseEntity<?> disableFishingInstructor(@PathVariable long id){
        fishingInstructorService.disableDisableFishingInstructor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteFishingInstructor(@PathVariable long id){
        fishingInstructorService.deleteFishingInstructor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/holiday/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<FishingInstructor> addHolidayToFishingInstructor(@PathVariable long id,
                                                                           @RequestBody HolidayTimespan holiday){
        return new ResponseEntity<>(fishingInstructorService.addHolidayToFishingInstructor(id, holiday),
                HttpStatus.CREATED);
    }

    @PutMapping(path = "/password", consumes = "application/json")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto passwordChangeDto){
        fishingInstructorService.changePasswordToFishingInstructor(passwordChangeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<FishingInstructor> updateFishingInstructor(@RequestBody FishingInstructor instructor){
        return new ResponseEntity<>(fishingInstructorService.updateFishingInstructor(instructor), HttpStatus.CREATED);
    }

    @PostMapping(path = "/passwordMatching", consumes = "application/json")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<String> isPasswordMatching(@RequestBody PasswordChangeDto passwordChangeDto){
        if(this.fishingInstructorService.isPasswordMatching(passwordChangeDto.getUserId(),
                passwordChangeDto.getPassword()))
            return new ResponseEntity<>("OK", HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
