package com.example.backend.Services;

import com.example.backend.Beans.BoatImage;
import com.example.backend.Beans.CottageImage;
import com.example.backend.Dtos.BoatImageDto;
import com.example.backend.Repository.BoatImageRepository;
import com.example.backend.Repository.CottageImageRepository;
import com.example.backend.Services.Interfaces.IBoatImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class BoatImageService implements IBoatImageService {

    @Autowired
    private BoatImageRepository boatImageRepository;
    @Autowired
    private BoatService boatService;

    public BoatImageService(BoatImageRepository boatImageRepository){ this.boatImageRepository = boatImageRepository;}

    @Override
    public Collection<BoatImage> findAllImages() {
        return boatImageRepository.findAll();
    }

    @Override
    public BoatImage findById(long id) {
        Optional<BoatImage> boatImage = boatImageRepository.findById(id);
        return boatImage.orElse(null);
    }

    @Override
    public BoatImage saveImage(BoatImageDto newImage) {
        BoatImage boatImage = new BoatImage();
        boatImage.setBoat(boatService.findById(newImage.getBoatId()));
        boatImage.setBase64(newImage.getBase64());
        return boatImageRepository.save(boatImage);
    }

    @Override
    public BoatImage updateImage(BoatImageDto newImage) {
        BoatImage boatImage = findById(newImage.getBoatId());
        boatImage.setBoat(boatService.findById(newImage.getBoatId()));
        boatImage.setBase64(newImage.getBase64());
        return boatImageRepository.save(boatImage);
    }

    @Override
    public void deleteImage(long id) {
        boatImageRepository.deleteById(id);
    }
}
