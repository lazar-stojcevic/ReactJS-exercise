package com.example.backend.Controllers;

import com.example.backend.Beans.Boat;
import com.example.backend.Beans.BoatImage;
import com.example.backend.Dtos.BoatImageDto;
import com.example.backend.Services.BoatImageService;
import com.example.backend.Services.BoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("boatImage")
public class BoatImageController {
    @Autowired
    private BoatImageService imageService;

    @Autowired
    private BoatService boatService;

    public BoatImageController(BoatImageService imageService){this.imageService = imageService;}

    @GetMapping(path = "/{id}")
    public ResponseEntity<Collection<BoatImage>> getCottageImageById(@PathVariable long id){
        Collection<BoatImage> images = boatService.findById(id).getImages();
        if (images != null)
            return new ResponseEntity<>(images, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    public BoatImage save(@RequestBody BoatImageDto imageDto){
        return this.imageService.saveImage(imageDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
