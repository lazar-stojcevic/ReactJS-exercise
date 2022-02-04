package com.example.backend.Services;

import com.example.backend.Beans.AdditionalService;
import com.example.backend.Repository.AdditionalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdditionalServiceService {
    @Autowired
    private final AdditionalServiceRepository additionalServiceRepository;

    @Autowired
    private AdventureService adventureService;

    public AdditionalServiceService(AdditionalServiceRepository additionalServiceRepository){
        this.additionalServiceRepository = additionalServiceRepository;
    }

    public AdditionalService findAdditionalServiceById(long id){
        return additionalServiceRepository.findById(id).orElse(null);
    }

    public AdditionalService addAdditionalServices(long adventureId, AdditionalService additionalServices){
        additionalServices.setPriceList(adventureService.findAdventureById(adventureId).getPriceList());
        return additionalServiceRepository.save(additionalServices);
    }


    public Collection<AdditionalService> getAllAdditionalServicesOfAdventure(long adventureId) {
        return adventureService.findAdventureById(adventureId).getPriceList().getAdditionalServices();
    }
}
