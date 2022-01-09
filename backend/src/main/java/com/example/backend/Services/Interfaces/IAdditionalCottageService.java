package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.AdditionalBoatService;
import com.example.backend.Beans.AdditionalCottageService;
import com.example.backend.Dtos.AdditionalServiceDto;

public interface IAdditionalCottageService {

    AdditionalCottageService findAdditionalServiceById(long id);

    AdditionalCottageService createCottageService(AdditionalServiceDto serviceDto);

    void delete(long id);

}
