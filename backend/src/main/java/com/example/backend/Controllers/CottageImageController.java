package com.example.backend.Controllers;

import com.example.backend.Beans.CottageImage;
import com.example.backend.Beans.Room;
import com.example.backend.Dtos.CottageImageDto;
import com.example.backend.Dtos.RoomDto;
import com.example.backend.Services.CottageImageService;
import com.example.backend.Services.CottageService;
import com.example.backend.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.*;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("cottageImage")
public class CottageImageController {
    @Autowired
    private CottageImageService imageService;

    @Autowired
    private CottageService cottageService;

    public CottageImageController(CottageImageService imageService){this.imageService = imageService;}

    @GetMapping(path = "/{id}")
    public ResponseEntity<Collection<CottageImage>> getCottageImageById(@PathVariable long id){
        Collection<CottageImage> images = cottageService.findById(id).getImages();
        if (images != null)
            return new ResponseEntity<>(images, HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    public CottageImage save(@RequestBody CottageImageDto imageDto){
        return this.imageService.saveImage(imageDto);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> delete(@PathVariable long id){
        imageService.deleteImage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
