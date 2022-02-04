package com.example.backend.Controllers;

import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Dtos.FishingInstructorChangeDto;
import com.example.backend.Dtos.AvailableTimespanDto;
import com.example.backend.Dtos.NewSubcriptionDto;
import com.example.backend.Dtos.PasswordChangeDto;
import com.example.backend.Services.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping(path = "fishingInstructor")
public class FishingInstructorController {

    @Autowired
    private final FishingInstructorService fishingInstructorService;

    public FishingInstructorController(FishingInstructorService fishingInstructorService){
        this.fishingInstructorService = fishingInstructorService;
    }

    @GetMapping(path = "/getAll")
    public ResponseEntity<Collection<FishingInstructor>> getAll(){
        return new ResponseEntity<>(fishingInstructorService.getAllFishingInstructors(), HttpStatus.OK);
    }

    @GetMapping (path = "/{id}")
    public ResponseEntity<FishingInstructor> getInstructorById(@PathVariable long id){
        return new ResponseEntity<>(fishingInstructorService.findFishingInstructorById(id), HttpStatus.OK);
    }

    @GetMapping (path = "isSubcribed/{instructorId}/{userId}")
    public ResponseEntity<Boolean> isCustomerSubscribedToInstuctor(@PathVariable long instructorId, @PathVariable long userId){

        return new ResponseEntity<>(fishingInstructorService.isUserSubcribed(instructorId, userId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteFishingInstructor(@PathVariable long id){
        fishingInstructorService.deleteFishingInstructor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/holiday/{id}", consumes = "application/json")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<FishingInstructor> addHolidayToFishingInstructor(@PathVariable long id,
                                                                           @RequestBody AvailableTimespanDto holiday){
        return new ResponseEntity<>(fishingInstructorService.addHolidayToFishingInstructor(id, holiday),
                HttpStatus.CREATED);
    }

    @PutMapping(path = "/password", consumes = "application/json")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto passwordChangeDto){
        fishingInstructorService.changePasswordToFishingInstructor(passwordChangeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/subscribe", consumes = "application/json")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> subscribe(@RequestBody NewSubcriptionDto newSubcriptionDto){
        try {
            fishingInstructorService.newSubscription(newSubcriptionDto);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/unsubscribe", consumes = "application/json")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> unsubscribe(@RequestBody NewSubcriptionDto forUnsubscribe){
        try {
            fishingInstructorService.unsubscribe(forUnsubscribe);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<FishingInstructor> updateFishingInstructor(
            @RequestBody FishingInstructorChangeDto fishingInstructorChangeDto){
        return new ResponseEntity<>(fishingInstructorService.updateFishingInstructor(fishingInstructorChangeDto),
                HttpStatus.CREATED);
    }
}
