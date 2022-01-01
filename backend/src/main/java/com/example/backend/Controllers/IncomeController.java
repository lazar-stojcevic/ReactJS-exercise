package com.example.backend.Controllers;

import com.example.backend.Dtos.IncomeDto;
import com.example.backend.Services.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("income")
public class IncomeController {
    @Autowired
    private final IncomeService incomeService;

    public IncomeController(IncomeService incomeService){
        this.incomeService = incomeService;
    }

    @PostMapping(path = "/instructorIncome")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<?> getIncomeOfInstructor(@RequestBody IncomeDto dto){
        incomeService.prepareForCalculatingInstructorIncome(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
