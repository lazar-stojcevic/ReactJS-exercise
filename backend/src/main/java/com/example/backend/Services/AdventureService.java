package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Repository.AdventureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class AdventureService {
    @Autowired
    private final AdventureRepository adventureRepository;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private CustomerService customerService;

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

    public void deleteAdventure(long id){
        adventureRepository.deleteById(id);
    }

    //TODO: NEMOGUCE JE OBRISATI AVANTURU DOK GOD IMA SLOBODNIH REZERVACIJA
    public boolean canAdventureBeDeleted(long id){
        for(AdventureReservation ar : findAdventureById(id).getReservations())
            if(ar.getReservationStart().isAfter(LocalDateTime.now()))
                return false;

        deleteAdventure(id);
        return true;
    }

    public Collection<Adventure> getAllAdventuresOfInstructor(long instructorId) {
        FishingInstructor instructor = fishingInstructorService.findFishingInstructorById(instructorId);
        return instructor.getAdventures();
    }

    public Collection<AdditionalService> getAdditionalServicesOfAdventure(long adventureId) {
        return findAdventureById(adventureId).getPriceList().getAdditionalServices();
    }

    public List<Image> getAllImagesOfAdventure(long adventureId){
        Adventure adventure = findAdventureById(adventureId);
        return adventure.getImages();
    }

    public Adventure prepaidCustomerToAdventure(long customerId, long adventureId){
        Customer customer = customerService.findCustomerById(customerId);
        Adventure adventure = findAdventureById(adventureId);
        adventure.getPrepaidCustomers().add(customer);
        return adventureRepository.save(adventure);
    }
}
