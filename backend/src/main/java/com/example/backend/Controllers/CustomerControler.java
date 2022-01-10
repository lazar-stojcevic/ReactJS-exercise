package com.example.backend.Controllers;

import com.example.backend.Beans.Boat;
import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.Customer;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Dtos.CustomerChangeDto;
import com.example.backend.Dtos.DeleteUserRequestDto;
import com.example.backend.Dtos.PasswordChangeDto;
import com.example.backend.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping(path = "customer")
public class CustomerControler {

    @Autowired
    private final CustomerService customerService;

    public CustomerControler(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Collection<Customer>> getAll(){
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping (path = "/{id}")
    @PreAuthorize("hasRole('CUSTOMER') || hasRole('ROLE_COTTAGE_OWNER')")
    public ResponseEntity<Customer> getCustomerById(@PathVariable long id){
        return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
    }

    @GetMapping (path = "/penalty/{id}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Integer> getCustomerPenaltyPoints(@PathVariable long id){
        return new ResponseEntity<>(customerService.getCustomerPenaltyPoints(id), HttpStatus.OK);
    }


    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCustomer(@PathVariable long id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/password", consumes = "application/json")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeDto passwordChangeDto){
        customerService.changePasswordToCustomer(passwordChangeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Customer> updateCustomer(
            @RequestBody CustomerChangeDto customerChangeDto){
        return new ResponseEntity<>(customerService.updateCustomer(customerChangeDto),
                HttpStatus.CREATED);
    }

    @PostMapping(path = "/passwordMatching", consumes = "application/json")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> isPasswordMatching(@RequestBody PasswordChangeDto passwordChangeDto){
        if(this.customerService.isPasswordMatching(passwordChangeDto.getUserId(),
                passwordChangeDto.getPassword()))
            return new ResponseEntity<>("OK", HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping(path = "/delete")
    public ResponseEntity<String> sendDeleteRequest(@RequestBody DeleteUserRequestDto deleteRequestDto){
        if(this.customerService.sendDeleteProfileRequest(deleteRequestDto))
            return new ResponseEntity<>("OK", HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @GetMapping (path = "/instructorSubscriptions/{userId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Collection<FishingInstructor>> getAllCustomersInstructorSubscriptions(@PathVariable long userId){
        return new ResponseEntity<>(customerService.getCustomerInstructorsSubscription(userId), HttpStatus.OK);
    }

    @GetMapping (path = "/cottageSubscriptions/{userId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Collection<Cottage>> getAllCustomersCottageSubscriptions(@PathVariable long userId){
        return new ResponseEntity<>(customerService.getCustomerCottagesSubscription(userId), HttpStatus.OK);
    }

    @GetMapping (path = "/boatSubscriptions/{userId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Collection<Boat>> getAllCustomersBoatSubscriptions(@PathVariable long userId){
        return new ResponseEntity<>(customerService.getCustomerBoatSubscription(userId), HttpStatus.OK);
    }
}
