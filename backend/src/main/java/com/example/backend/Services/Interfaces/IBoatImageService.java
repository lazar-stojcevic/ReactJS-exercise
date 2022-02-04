package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.BoatImage;
import com.example.backend.Dtos.BoatImageDto;
import com.example.backend.Dtos.CottageImageDto;

import java.util.Collection;

public interface IBoatImageService {
    Collection<BoatImage> findAllImages();
    BoatImage findById(long id);
    BoatImage saveImage(BoatImageDto newImage);
    BoatImage updateImage(BoatImageDto newImage);
    void deleteImage(long id);
}
