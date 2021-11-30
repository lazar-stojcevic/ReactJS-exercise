package com.example.backend.Services;

import com.example.backend.Beans.Adventure;
import com.example.backend.Beans.FishingInstructor;
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
        return adventureRepository.findById(id).orElse(null);
    }

    //PITANJE JE DA LI JE OVAKO
    public Adventure updateAdventure(Adventure adventure){
        return adventureRepository.save(adventure);
    }

    public Collection<Adventure> getAllAdventures(){
        return adventureRepository.findAll();
    }

    public Adventure saveAdventure(Adventure adventure){
        return adventureRepository.save(adventure);
    }

    public void deleteAdventure(long id){
        adventureRepository.deleteById(id);
    }

    public Collection<Adventure> getAllAdventuresOfInstructor(long instructorId) {
        FishingInstructor instructor = fishingInstructorService.findFishingInstructorById(instructorId);
        return instructor.getAdventures();
    }
}
