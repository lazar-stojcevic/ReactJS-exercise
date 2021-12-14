package com.example.backend.Services;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Dtos.MakeFastReservationDto;
import com.example.backend.Repository.AdventureReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AdventureReservationService {

    @Autowired
    private final AdventureReservationRepository adventureReservationRepository;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    public AdventureReservationService(AdventureReservationRepository repository){
        this.adventureReservationRepository = repository;
    }

    public Collection<AdventureReservation> getAllAdventureReservations(){
        return adventureReservationRepository.findAll();
    }

    public Collection<AdventureReservation> getAllNextReservedTermsOfAdventure(long adventureId){
        List<AdventureReservation> adventureReservations = new ArrayList<>();
        for (AdventureReservation ar : adventureReservationRepository.findAll())
            if(ar.isReserved() && ar.getAdventure().getId() == adventureId &&
                    ar.getReservationStart().isAfter(LocalDateTime.now()))
                adventureReservations.add(ar);

        return adventureReservations;
    }

    public Collection<AdventureReservation> getAllFreeFastReservations(long adventureId){
        List<AdventureReservation> adventureReservations = new ArrayList<>();
        for (AdventureReservation ar: getAllAdventureReservations())
            if(!ar.isReserved() && ar.getAdventure().getId() == adventureId)
                adventureReservations.add(ar);

        return adventureReservations;
    }

    public Collection<AdventureReservation> getAllPastReservationOfInstructor(long instructorId){
        List<AdventureReservation> pastAdventure = new ArrayList<>();
        for (AdventureReservation ar: adventureReservationRepository.findAll()) {
            LocalDateTime reservationEnd = ar.getReservationStart().plusHours(ar.getLength());
            if(reservationEnd.isBefore(LocalDateTime.now()) && ar.isReserved() &&
                    ar.getAdventure().getInstructor().getId() == instructorId){
                pastAdventure.add(ar);
            }
        }
        return pastAdventure;
    }

    public AdventureReservation findAdventureReservationById(long id){
        return adventureReservationRepository.findById(id).orElse(null);
    }

    public AdventureReservation createFreeFastReservation(MakeFastReservationDto dto){
        for(AdventureReservation ar : getAllNextReservationsOfInstructor(dto.getInstructorId())){
            if(!isReservationInAvailableTimespanOfInstructor(dto.getAdventureReservation(), dto.getInstructorId()))
                return null;
            if(isReservationsOverlap(ar, dto.getAdventureReservation()))
                return null;
        }
        dto.getAdventureReservation().setAdventure(adventureService.findAdventureById(dto.getAdventureId()));
        return adventureReservationRepository.save(dto.getAdventureReservation());
    }

    public boolean deleteAdventureReservation(long id){
        AdventureReservation adventureReservation = findAdventureReservationById(id);
        if(adventureReservation.isReserved())
            return false;
        adventureReservationRepository.deleteById(id);
        return true;
    }

    public AdventureReservation cancelAdventureReservation(long id){
        AdventureReservation adventureReservation = findAdventureReservationById(id);
        adventureReservation.setReserved(false);
        return adventureReservationRepository.save(adventureReservation);
    }

    public AdventureReservation makeReportOfAdventure(long id, String report){
        AdventureReservation adventureReservation = findAdventureReservationById(id);
        adventureReservation.setReport(report);
        return adventureReservationRepository.save(adventureReservation);
    }

    private Collection<AdventureReservation> getAllNextReservationsOfInstructor(long instructorId){
        List<AdventureReservation> allNextReservations = new ArrayList<>();
        for(AdventureReservation ar : getAllAdventureReservations())
            if(ar.getAdventure().getInstructor().getId() == instructorId &&
                    ar.getReservationStart().isAfter(LocalDateTime.now()))
                allNextReservations.add(ar);

        return allNextReservations;
    }

    //METODA PROVERAVA DA LI SE DVA TERMINA PREKLAPAJU
    private boolean isReservationsOverlap(
            AdventureReservation existingReservation, AdventureReservation newReservation){
        LocalDateTime existingReservationEndTime = existingReservation.getReservationStart()
                .plusHours(existingReservation.getLength());
        LocalDateTime newReservationEndTime = newReservation.getReservationStart()
                .plusHours(newReservation.getLength());
        if(newReservation.getReservationStart().isAfter(existingReservation.getReservationStart()) &&
                newReservation.getReservationStart().isBefore(existingReservationEndTime))
            return true;
        else if(newReservationEndTime.isAfter(existingReservation.getReservationStart()) &&
                newReservationEndTime.isBefore(existingReservationEndTime))
            return true;
        else return newReservation.getReservationStart().isBefore(existingReservation.getReservationStart()) &&
                    newReservationEndTime.isAfter(existingReservationEndTime);
    }

    private boolean isReservationInAvailableTimespanOfInstructor(
            AdventureReservation adventureReservation, long instructorId) {
        LocalDateTime reservationEnd = adventureReservation.getReservationStart()
                .plusHours(adventureReservation.getLength());
        FishingInstructor instructor = fishingInstructorService.findFishingInstructorById(instructorId);

        return adventureReservation.getReservationStart().isAfter(instructor.getAvailable().getFromDate()) &&
                reservationEnd.isBefore(instructor.getAvailable().getToDate());
    }
}
