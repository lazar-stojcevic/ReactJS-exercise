package com.example.backend.Controllers;

import com.example.backend.Beans.Adventure;
import com.example.backend.Services.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("adventure")
public class AdventureController {
    @Autowired
    private final AdventureService adventureService;

    public AdventureController(AdventureService adventureService){
        this.adventureService = adventureService;
    }

    @GetMapping()
    public ResponseEntity<Collection<Adventure>> getAllAdventure(){
        return new ResponseEntity<>(adventureService.getAllAdventures(), HttpStatus.OK);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Adventure> getAdventureById(@PathVariable long id){
        Adventure adventure = adventureService.findAdventureById(id);
        if(adventure != null)
            return new ResponseEntity<>(adventure, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(path = "/instructor/{instructorId}")
    public ResponseEntity<Collection<Adventure>> getAllAdventuresOfInstructor(@PathVariable long instructorId){
        return new ResponseEntity<>(adventureService.getAllAdventuresOfInstructor(instructorId), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Adventure> saveAdventure(@RequestBody Adventure adventure){
        return new ResponseEntity<>(adventureService.saveAdventure(adventure), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteAdventure(@PathVariable long id){
        adventureService.deleteAdventure(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<Adventure> updateAdventure(@RequestBody Adventure adventure){
        return new ResponseEntity<>(adventureService.updateAdventure(adventure), HttpStatus.OK);
    }

}
