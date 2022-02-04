package com.example.backend.Services;

import com.example.backend.Beans.AvailablePeriodBoat;
import com.example.backend.Beans.AvailablePeriodCottage;
import com.example.backend.Dtos.AvailablePeriodDto;
import com.example.backend.Repository.AvailablePeriodBoatRepository;
import com.example.backend.Repository.AvailablePeriodCottageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AvailablePeriodsBoatService {
    @Autowired
    private final AvailablePeriodBoatRepository availablePeriodBoatRepository;

    @Autowired
    private BoatService boatService;


    public AvailablePeriodsBoatService(AvailablePeriodBoatRepository availablePeriodBoatRepository){
        this.availablePeriodBoatRepository = availablePeriodBoatRepository;
    }

    public AvailablePeriodBoat findPeriodById(long id){
        return availablePeriodBoatRepository.findById(id).orElse(null);
    }

    public AvailablePeriodBoat createBoatAvailablePeriod(AvailablePeriodDto availableTimespan) {
        AvailablePeriodBoat availablePeriodBoat = new AvailablePeriodBoat();
        availablePeriodBoat.setFromDate(availableTimespan.getFromDate());
        availablePeriodBoat.setToDate(availableTimespan.getToDate());
        availablePeriodBoat.setBoat(boatService.findById(availableTimespan.getEntityId()));
        return availablePeriodBoatRepository.save(availablePeriodBoat);
    }

    public AvailablePeriodBoat updateBoatAvailablePeriod(AvailablePeriodDto availableTimespan){
        AvailablePeriodBoat availablePeriodBoat = findPeriodById(availableTimespan.getId());
        availablePeriodBoat.setFromDate(availableTimespan.getFromDate());
        availablePeriodBoat.setToDate(availableTimespan.getToDate());
        availablePeriodBoat.setBoat(boatService.findById(availableTimespan.getEntityId()));
        return availablePeriodBoatRepository.save(availablePeriodBoat);
    }

    public void deleteBoatAvailablePeriod(long id){
        availablePeriodBoatRepository.deleteById(id);
    }
}
