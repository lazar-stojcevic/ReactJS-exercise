package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.CottageImage;
import com.example.backend.Beans.Room;
import com.example.backend.Dtos.CottageImageDto;
import com.example.backend.Dtos.RoomDto;

import java.util.Collection;

public interface ICottageImageService {
    Collection<CottageImage> findAllImages();
    CottageImage findById(long id);
    CottageImage saveImage(CottageImageDto newImage);
    CottageImage updateImage(CottageImageDto newImage);
    void deleteImage(long id);
}
