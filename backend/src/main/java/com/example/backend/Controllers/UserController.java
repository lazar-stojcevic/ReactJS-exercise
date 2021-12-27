package com.example.backend.Controllers;

import com.example.backend.Beans.User;
import com.example.backend.Dtos.AnswerOnRequestForDeletingDto;
import com.example.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping(path = "user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/notEnabled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<User>> allUsersForEnabling(){
        return new ResponseEntity<>(userService.getAllNotEnabledUsers(), HttpStatus.OK);
    }

    @PutMapping(path = "/enable/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> enableFishingInstructor(@PathVariable long id){
        User user = userService.enableUser(id);
        if(user== null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/disable/{id}/{reason}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> disableFishingInstructor(@PathVariable long id, @PathVariable String reason){
        boolean isDeleted = userService.disableUser(id, reason);
        if(!isDeleted)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(path = "/answerOnRequestFroDeleting")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@RequestBody AnswerOnRequestForDeletingDto dto){
        userService.answerOnRequestForDeleting(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
