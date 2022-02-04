package com.example.backend.Services;

import com.example.backend.Beans.AdditionalCottageService;
import com.example.backend.Beans.AvailablePeriodCottage;
import com.example.backend.Beans.AvailableTimespan;
import com.example.backend.Dtos.AdditionalServiceDto;
import com.example.backend.Dtos.AvailablePeriodDto;
import com.example.backend.Repository.AdditionalCottageServiceRepository;
import com.example.backend.Repository.AvailablePeriodCottageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailablePeriodCottageService {
    @Autowired
    private final AvailablePeriodCottageRepository availablePeriodCottageRepository;

    @Autowired
    private CottageService cottageService;


    public AvailablePeriodCottageService(AvailablePeriodCottageRepository availablePeriodCottageService){
        this.availablePeriodCottageRepository = availablePeriodCottageService;
    }

    public AvailablePeriodCottage findPeriodById(long id){
        return availablePeriodCottageRepository.findById(id).orElse(null);
    }

    public AvailablePeriodCottage createCottageAvailablePeriod(AvailablePeriodDto availableTimespan) {
        AvailablePeriodCottage availablePeriodCottage = new AvailablePeriodCottage();
        availablePeriodCottage.setFromDate(availableTimespan.getFromDate());
        availablePeriodCottage.setToDate(availableTimespan.getToDate());
        availablePeriodCottage.setCottage(cottageService.findById(availableTimespan.getEntityId()));
        return availablePeriodCottageRepository.save(availablePeriodCottage);
    }

    public AvailablePeriodCottage updateCottageAvailablePeriod(AvailablePeriodDto availableTimespan){
        AvailablePeriodCottage availablePeriodCottage = findPeriodById(availableTimespan.getId());
        availablePeriodCottage.setFromDate(availableTimespan.getFromDate());
        availablePeriodCottage.setToDate(availableTimespan.getToDate());
        availablePeriodCottage.setCottage(cottageService.findById(availableTimespan.getEntityId()));
        return availablePeriodCottageRepository.save(availablePeriodCottage);
    }

    public void deleteCottageAvailablePeriod(long id){
        availablePeriodCottageRepository.deleteById(id);
    }
}
