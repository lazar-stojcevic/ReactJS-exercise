package com.example.backend.Controllers;

import com.example.backend.Beans.Image;
import com.example.backend.Services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping(path = "images")
public class ImageController {
    @Autowired
    private final ImageService imageService;

    public ImageController(ImageService imageService){
        this.imageService = imageService;
    }

    @PostMapping(path = "/{adventureId}")
    @PreAuthorize("hasRole('INSTRUCTOR')")
    public ResponseEntity<Image> addAdditionalServices(
            @PathVariable long adventureId, @RequestBody String image){
        return new ResponseEntity<>(imageService.saveImage(image, adventureId), HttpStatus.CREATED);
    }
}
