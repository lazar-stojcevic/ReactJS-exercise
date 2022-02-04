package com.example.backend.Services;

import com.example.backend.Beans.Image;
import com.example.backend.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {
    @Autowired
    private final ImageRepository imageRepository;

    @Autowired
    private AdventureService adventureService;

    public ImageService(ImageRepository imageRepository){
        this.imageRepository = imageRepository;
    }

    public Image saveImage(String imageString, long adventureId){
        Image image = new Image();
        image.setAdventure(adventureService.findAdventureById(adventureId));
        image.setImage(imageString);
        return imageRepository.save(image);
    }
}
