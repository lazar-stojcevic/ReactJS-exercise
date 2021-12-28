package com.example.backend.Controllers;

import com.example.backend.Beans.AdventureComplaint;
import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Dtos.NewComplaintDto;
import com.example.backend.Dtos.ReservationSearchDto;
import com.example.backend.Dtos.ReviewComplaintDto;
import com.example.backend.Services.ComplaintService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("complaint")
public class ComplaintController {
    @Autowired
    private final ComplaintService complaintService;

    public ComplaintController(ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @PostMapping(path = "/adventure")
    public ResponseEntity<Boolean> getAllAvailableAdventures(@RequestBody NewComplaintDto complaintDto){
        return new ResponseEntity<>(complaintService.SaveAdventureComplaint(complaintDto),
                HttpStatus.OK);
    }

    @GetMapping(path = "/adventureComplaint")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<AdventureComplaint>> getAllAdventureComplaintForRevision(){
        return new ResponseEntity<>(complaintService.getAllNotReviewedAdventureComplaint(), HttpStatus.OK);
    }

    @PutMapping(path = "/reviewComplaint")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> makeReviewOnComplaint(@RequestBody ReviewComplaintDto dto){
        if(complaintService.reviewComplaint(dto))
            return new ResponseEntity<>(HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
