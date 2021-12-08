package com.example.backend.Controllers;

import com.example.backend.Beans.Customer;
import com.example.backend.Beans.Address;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Beans.User;
import com.example.backend.Dtos.JwtAuthenticationRequest;
import com.example.backend.Dtos.UserRegistration;
import com.example.backend.Dtos.UserTokenState;
import com.example.backend.Security.util.TokenUtils;
import com.example.backend.Services.CustomerService;
import com.example.backend.Services.EmailService;
import com.example.backend.Services.FishingInstructorService;
import com.example.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;

@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthenticationController {

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private UserService userService;
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
        //DODATI USER ROLE I ID
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, user.getId(), user.getRole().getName()));
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

        emailService.sendNotificaitionAsync(user);

        //Dodajte vase registracije
        //TODO: dodaj register za ostale

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{email}")
    public ResponseEntity<String> verifyCustomer(@PathVariable("email") String email){
        Customer existUser = this.customerService.findByEmail(email);

        existUser.setEnabled(true);

        customerService.verifyUser(existUser);

        return new ResponseEntity<>("User verified", HttpStatus.CREATED);
    }

    @GetMapping("/notEnabled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<User>> allUsersForEnabling(){
        return new ResponseEntity<>(userService.getAllNotEnabledUsers(), HttpStatus.OK);
    }

    private User tryToRegisterFishingInstructor(UserRegistration userRequest) {
        try {
            return (User)fishingInstructorService.saveFishingInstructor(userRequest);
        }catch (Exception e){
            return null;
        }
    }
}
