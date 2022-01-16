package com.example.backend.Controllers;

import com.example.backend.Beans.Grade;
import com.example.backend.Dtos.GradeToSaveDto;
import com.example.backend.Dtos.GradeToShowDto;
import com.example.backend.Services.GradeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("grade")
public class GradeController {
    @Autowired
    private final GradeService gradeService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public GradeController(GradeService gradeService){
        this.gradeService = gradeService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Grade>> getAllGrades(){
        return new ResponseEntity<>(gradeService.getAllGrades(), HttpStatus.OK);
    }

    @GetMapping(path = "/instructor/{id}")
    public ResponseEntity<GradeToShowDto> getAllGradesOfInstructor(@PathVariable long id){
        return new ResponseEntity<>(gradeService.getAllGradesOfInstructor(id), HttpStatus.OK);
    }

    @GetMapping(path = "/revision")
    public ResponseEntity<Collection<Grade>> getAllGradesForRevision(){
        return new ResponseEntity<>(gradeService.getAllGradesForRevision(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Grade> saveGrade(@RequestBody GradeToSaveDto dto){
        return new ResponseEntity<>(gradeService.makeGradeForSaving(dto), HttpStatus.CREATED);
    }

    @GetMapping(path = "/disable/{id}")
    public ResponseEntity<?> disableGrade(@PathVariable long id){
        gradeService.enableOrDisableGrade(id, false);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(path = "/enable/{id}")
    public ResponseEntity<?> enableGrade(@PathVariable long id){
        gradeService.enableOrDisableGrade(id, true);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
