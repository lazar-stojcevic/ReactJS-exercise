package com.example.backend.Services;

import com.example.backend.Beans.AdditionalServices;
import com.example.backend.Beans.Adventure;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Beans.PriceList;
import com.example.backend.Repository.AdventureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AdventureService {
    @Autowired
    private final AdventureRepository adventureRepository;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    public AdventureService(AdventureRepository adventureRepository){
        this.adventureRepository = adventureRepository;
    }

    public Adventure findAdventureById(long id){
        return adventureRepository.findById(id).get();
    }

    //DTO OBJEKAT ZA IZMENU
    public Adventure updateAdventure(Adventure adventure){
        PriceList priceList = findAdventureById(adventure.getId()).getPriceList();
        priceList.setPrice(adventure.getPriceList().getPrice());
        adventure.setPriceList(priceList);
        return adventureRepository.save(adventure);
    }

    public Collection<Adventure> getAllAdventures(){
        return adventureRepository.findAll();
    }

    public Adventure saveAdventure(long instructorId, Adventure adventure){
        adventure.setInstructor(fishingInstructorService.findFishingInstructorById(instructorId));
        return adventureRepository.save(adventure);
    }

    //provere da li ima rezervisanih termina
    public void deleteAdventure(long id){
        adventureRepository.deleteById(id);
    }

    public Collection<Adventure> getAllAdventuresOfInstructor(long instructorId) {
        FishingInstructor instructor = fishingInstructorService.findFishingInstructorById(instructorId);
        return instructor.getAdventures();
    }

    public Collection<AdditionalServices> getAdditionalServicesOfAdventure(long adventureId) {
        return findAdventureById(adventureId).getPriceList().getAdditionalServices();
    }
}
