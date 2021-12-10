package com.example.backend.Services;

import com.example.backend.Beans.Cottage;
import com.example.backend.Beans.CottageImage;
import com.example.backend.Dtos.CottageImageDto;
import com.example.backend.Repository.CottageImageRepository;
import com.example.backend.Services.Interfaces.ICottageImageService;
import com.example.backend.Services.Interfaces.ICottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CottageImageService implements ICottageImageService {

    @Autowired
    private CottageImageRepository cottageImageRepository;
    @Autowired
    private CottageService cottageService;

    public CottageImageService(CottageImageRepository cottageImageRepository){this.cottageImageRepository = cottageImageRepository;}

    @Override
    public Collection<CottageImage> findAllImages() {
        return cottageImageRepository.findAll();
    }

    @Override
    public CottageImage findById(long id) {
        Optional<CottageImage> cottage = cottageImageRepository.findById(id);
        return cottage.orElse(null);
    }

    @Override
    public CottageImage saveImage(CottageImageDto newImage) {
        CottageImage cottageImage = new CottageImage();
        cottageImage.setCottage(cottageService.findById(newImage.getCottageId()));
        cottageImage.setBase64(newImage.getBase64());
        return cottageImageRepository.save(cottageImage);
    }

    @Override
    public CottageImage updateImage(CottageImageDto newImage) {
        CottageImage cottageImage = findById(newImage.getId());
        cottageImage.setCottage(cottageService.findById(newImage.getCottageId()));
        cottageImage.setBase64(newImage.getBase64());
        return cottageImageRepository.save(cottageImage);
    }

    @Override
    public void deleteImage(long id) {
        cottageImageRepository.deleteById(id);
    }
}
