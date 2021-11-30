package com.example.backend.Controllers;

import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Beans.HolidayTimespan;
import com.example.backend.Dtos.PasswordChangeDto;
import com.example.backend.Services.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path = "fishingInstructor")
public class FishingInstructorController {

    @Autowired
    private final FishingInstructorService fishingInstructorService;

    public FishingInstructorController(FishingInstructorService fishingInstructorService){
        this.fishingInstructorService = fishingInstructorService;
    }

    //TODO: DODATI AUTORIZACIJU
    @GetMapping
    public Collection<FishingInstructor> getAll(){
        return fishingInstructorService.getAllFishingInstructors();
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
    public ResponseEntity<FishingInstructor> addHolidayToFishingInstructor(@PathVariable long id,
                                                                           @RequestBody HolidayTimespan holiday){
        return new ResponseEntity<>(fishingInstructorService.addHolidayToFishingInstructor(id, holiday),
                HttpStatus.CREATED);
    }

    @PostMapping(path = "/password", consumes = "application/json")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto passwordChangeDto){
        fishingInstructorService.changePasswordToFishingInstructor(passwordChangeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
