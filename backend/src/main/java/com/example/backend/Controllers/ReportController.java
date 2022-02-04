package com.example.backend.Controllers;

import com.example.backend.Beans.Report;
import com.example.backend.Dtos.MakeReportDto;
import com.example.backend.Services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("report")
public class ReportController {
    @Autowired
    private final ReportService reportService;

    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @GetMapping(path = "/notProcessed")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Collection<Report>> getAllNotProcessedReports(){
        return new ResponseEntity<>(reportService.getAllNotProcessedReports(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createNewReport(@RequestBody MakeReportDto dto){
        Report report = reportService.makeReportForSaving(dto);
        if(report == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(path = "/penalize/{reportId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> penalizeCustomer(@PathVariable long reportId){
        Report report = reportService.penalizeCustomer(reportId);
        if(report == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/mercy/{reportId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> haveMarcyOnCustomer(@PathVariable long reportId){
        Report report = reportService.haveMercyOnCustomer(reportId);
        if(report == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(HttpStatus.OK);
    }
}
