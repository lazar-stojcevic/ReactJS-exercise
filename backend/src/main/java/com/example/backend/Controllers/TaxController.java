package com.example.backend.Controllers;

import com.example.backend.Beans.Tax;
import com.example.backend.Dtos.SystemIncomeDto;
import com.example.backend.Dtos.SystemIncomeForMail;
import com.example.backend.Services.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("tax")
public class TaxController {
    @Autowired
    private final TaxService taxService;

    public TaxController(TaxService taxService){
        this.taxService = taxService;
    }

    @GetMapping(path = "/current")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Tax> getCurrentTax(){
        return new ResponseEntity<>(taxService.getCurrentTax(), HttpStatus.OK);
    }

    @PutMapping(path = "/newTax/{taxRate}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeTax(@PathVariable double taxRate){
        Tax newTax = taxService.changeTax(taxRate);
        if(newTax != null)
            return new ResponseEntity<>(HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/calculateIncome")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SystemIncomeForMail> calculateIncome(@RequestBody SystemIncomeDto dto){
        //taxService.calculateIncome(dto);
        return new ResponseEntity<>(taxService.calculateIncome(dto), HttpStatus.OK);
    }
}
