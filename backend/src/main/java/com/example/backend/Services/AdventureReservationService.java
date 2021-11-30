package com.example.backend.Services;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Repository.AdventureReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AdventureReservationService {

    @Autowired
    private final AdventureReservationRepository adventureReservationRepository;

    public AdventureReservationService(AdventureReservationRepository repository){
        this.adventureReservationRepository = repository;
    }

    public Collection<AdventureReservation> getAllAdventureReservations(){
        return adventureReservationRepository.findAll();
    }

    public Collection<AdventureReservation> getAllFastAdventureReservations(){
        List<AdventureReservation> adventureReservations = new ArrayList<>();
        for (AdventureReservation ar: getAllAdventureReservations())
            if(!ar.isReserved())
                adventureReservations.add(ar);

        return adventureReservations;
    }

    public AdventureReservation findAdventureReservationById(long id){
        return adventureReservationRepository.findById(id).orElse(null);
    }

    //TODO: PROVERA DA LI JE FI SLOBODAN U MOMENTU KADA SE REZERVISE TERMIN
    public AdventureReservation saveAdventureReservation(AdventureReservation adventureReservation){
        return adventureReservationRepository.save(adventureReservation);
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
        adventureReservation.setCanceled(true);
        return adventureReservationRepository.save(adventureReservation);
    }

    public AdventureReservation makeReportOfAdventure(long id, String report){
        AdventureReservation adventureReservation = findAdventureReservationById(id);
        adventureReservation.setReport(report);
        return adventureReservationRepository.save(adventureReservation);
    }
    //TODO: BRZA REZERVACIJA ZA KLIJENTE, param clientId, adventureId
    //TODO: SVE AVANTURE JEDNOG KLIJENTA param clientId
}
