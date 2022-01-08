package com.example.backend.Controllers;

import com.example.backend.Beans.Complaint;
import com.example.backend.Dtos.NewComplaintDto;
import com.example.backend.Dtos.ReviewComplaintDto;
import com.example.backend.Services.ComplaintService;
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
    public ResponseEntity<Boolean> createAdventureComplaint(@RequestBody NewComplaintDto complaintDto){
        return new ResponseEntity<>(complaintService.SaveAdventureComplaint(complaintDto),
                HttpStatus.OK);
    }

    @PostMapping(path = "/cottage")
    public ResponseEntity<Boolean> createCottageComplaint(@RequestBody NewComplaintDto complaintDto){
        return new ResponseEntity<>(complaintService.SaveCottageComplaint(complaintDto),
                HttpStatus.OK);
    }

    @GetMapping(path = "/adventureComplaint")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<Complaint>> getAllAdventureComplaintForRevision(){
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
