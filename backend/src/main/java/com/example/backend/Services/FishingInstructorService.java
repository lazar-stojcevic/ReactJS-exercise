package com.example.backend.Services;

import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Repository.FishingInstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class FishingInstructorService {

    private final FishingInstructorRepository fishingInstructorRepository;

    @Autowired
    public FishingInstructorService(FishingInstructorRepository fishingInstructorRepository){
        this.fishingInstructorRepository = fishingInstructorRepository;
    }

    public Collection<FishingInstructor> getAll(){
        return fishingInstructorRepository.findAll();
    }

    public FishingInstructor save(FishingInstructor fishingInstructor){
        FishingInstructor instructor = null;
        //HENDLUJE SLUCAJ DA POSTOJI OSOBA SA ISTIM MEJLOM
        try {
             instructor = fishingInstructorRepository.save(fishingInstructor);
        } catch (Exception e){
            System.out.println(e);
        }
        return instructor;
    }
}
