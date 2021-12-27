package com.example.backend.Controllers;

import com.example.backend.Beans.DeleteProfileRequest;
import com.example.backend.Dtos.MakeDeleteProfileRequestDto;
import com.example.backend.Services.DeleteProfileRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("deleteRequest")
public class DeleteProfileRequestController {
    @Autowired
    private final DeleteProfileRequestService deleteProfileRequestService;

    public DeleteProfileRequestController(DeleteProfileRequestService deleteProfileRequestService){
        this.deleteProfileRequestService = deleteProfileRequestService;
    }

    @GetMapping(path = "/notReviewed")
    public ResponseEntity<Collection<DeleteProfileRequest>> getAllNotReviewedRequests(){
        return new ResponseEntity<>(deleteProfileRequestService.getAllNotReviewedRequestsForProfileDeleting(),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> saveDeleteProfileRequests(@RequestBody MakeDeleteProfileRequestDto dto){
        DeleteProfileRequest request = deleteProfileRequestService.makeDeleteProfileRequest(dto);
        if(request == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
