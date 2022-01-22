package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.Cottage;
import com.example.backend.Dtos.CottageDto;

import java.util.Collection;

public interface ICottageService {
    Collection<Cottage> findAllCottages();
    Cottage findById(long id);
    Cottage saveCottage(CottageDto newCottage);
    Cottage updateCottage(CottageDto changeDto);
}
