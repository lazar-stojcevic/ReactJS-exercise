package com.example.backend.Services;

import com.example.backend.Beans.AdventureComplaint;
import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Dtos.NewComplaintDto;
import com.example.backend.Repository.AdventureComplaintRepository;
import com.example.backend.Repository.AdventureRepository;
import com.example.backend.Repository.AdventureReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComplaintService {
    @Autowired
    private final AdventureReservationRepository adventureReservationRepository;
    @Autowired
    private final AdventureComplaintRepository adventureComplaintRepository;

    public ComplaintService(AdventureReservationRepository adventureReservationRepository, AdventureComplaintRepository adventureComplaintRepository) {
        this.adventureReservationRepository = adventureReservationRepository;
        this.adventureComplaintRepository = adventureComplaintRepository;
    }

    public boolean SaveAdventureComplaint(NewComplaintDto complaint){
        try {
            AdventureReservation ar = adventureReservationRepository.getById(complaint.getId());
            AdventureComplaint ac = new AdventureComplaint();
            ac.setReservation(ar);
            ac.setText(complaint.getText());
            ac.setReviewed(false);

            ar.setComplaint(ac);

            adventureComplaintRepository.save(ac);
            adventureReservationRepository.save(ar);
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
