package com.example.backend.Controllers;

import com.example.backend.Beans.Address;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Beans.User;
import com.example.backend.Dtos.JwtAuthenticationRequest;
import com.example.backend.Dtos.UserRegistration;
import com.example.backend.Dtos.UserTokenState;
import com.example.backend.Security.util.TokenUtils;
import com.example.backend.Services.CustomerService;
import com.example.backend.Services.FishingInstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    // Prvi endpoint koji pogadja korisnik kada se loguje.
    // Tada zna samo svoje korisnicko ime i lozinku i to prosledjuje na backend.
    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody JwtAuthenticationRequest authenticationRequest, HttpServletResponse response) {

        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    // Endpoint za registraciju novog korisnika
    //VREDNOSTI U userReques.getUserType: FI-FISHING INSTRUCTOR
    @PostMapping("/signup")
    public ResponseEntity<?> addUser(@RequestBody UserRegistration userRequest, UriComponentsBuilder ucBuilder) throws Exception {
        //REGISTRACIJA FISHING INSTRUKTORA
        if(Objects.equals(userRequest.getUserType(), "FI")){
            User user = tryToRegisterFishingInstructor(userRequest);
            if(user != null)
                return new ResponseEntity<>(HttpStatus.CREATED);
            else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        User existUser = this.customerService.findByEmail(userRequest.getEmail());
        if (existUser != null) {
            throw new Exception("Doslo je do greske");
        }
        User user = this.customerService.save(userRequest);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    private User tryToRegisterFishingInstructor(UserRegistration userRequest) {
        try {
            return (User)fishingInstructorService.saveFishingInstructor(userRequest);
        }catch (Exception e){
            return null;
        }
    }
}
