package com.example.backend.Controllers;

import com.example.backend.Beans.Customer;
import com.example.backend.Dtos.CustomerChangeDto;
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
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Customer> getInstructorById(@PathVariable long id){
        return new ResponseEntity<>(customerService.findCustomerById(id), HttpStatus.OK);
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
}
