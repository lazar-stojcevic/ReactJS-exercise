package com.example.backend.Services;

import com.example.backend.Beans.AdditionalServices;
import com.example.backend.Repository.AdditionalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdditionalServicesService {
    @Autowired
    private final AdditionalServiceRepository additionalServiceRepository;

    @Autowired
    private AdventureService adventureService;

    public AdditionalServicesService(AdditionalServiceRepository additionalServiceRepository){
        this.additionalServiceRepository = additionalServiceRepository;
    }

    public AdditionalServices addAdditionalServices(long adventureId, AdditionalServices additionalServices){
        additionalServices.setPriceList(adventureService.findAdventureById(adventureId).getPriceList());
        return additionalServiceRepository.save(additionalServices);
    }

}
