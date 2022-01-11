package com.example.backend.Services;

import com.example.backend.Beans.AdditionalBoatService;
import com.example.backend.Dtos.AdditionalServiceDto;
import com.example.backend.Repository.AdditionalBoatServiceRepository;
import com.example.backend.Services.Interfaces.IAdditionalBoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdditionalBoatServiceService implements IAdditionalBoatService {

    @Autowired
    private final AdditionalBoatServiceRepository additionalServiceRepository;

    @Autowired
    private BoatService boatService;


    public AdditionalBoatServiceService(AdditionalBoatServiceRepository additionalServiceRepository){
        this.additionalServiceRepository = additionalServiceRepository;
    }

    @Override
    public AdditionalBoatService findAdditionalServiceById(long id){
        return additionalServiceRepository.findById(id).orElse(null);
    }

    @Override
    public AdditionalBoatService createBoatService(AdditionalServiceDto serviceDto) {
        AdditionalBoatService additionalBoatService = new AdditionalBoatService();
        additionalBoatService.setName(serviceDto.getName());
        additionalBoatService.setAddPrice(serviceDto.getAddPrice());
        additionalBoatService.setPriceList(boatService.findById(serviceDto.getPriceList()).getPriceList());
        return additionalServiceRepository.save(additionalBoatService);
    }

    @Override
    public void delete(long id) {
        AdditionalBoatService service = findAdditionalServiceById(id);
        service.setPriceList(null);
        additionalServiceRepository.save(service);
        additionalServiceRepository.deleteById(id);
    }

}
