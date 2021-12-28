package com.example.backend.Services;

import com.example.backend.Beans.AdventureComplaint;
import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.User;
import com.example.backend.Dtos.NewComplaintDto;
import com.example.backend.Dtos.ReviewComplaintDto;
import com.example.backend.Repository.AdventureComplaintRepository;
import com.example.backend.Repository.AdventureReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ComplaintService {
    @Autowired
    private final AdventureReservationRepository adventureReservationRepository;
    @Autowired
    private final AdventureComplaintRepository adventureComplaintRepository;
    @Autowired
    private EmailService emailService;

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

    public Collection<AdventureComplaint> getAllNotReviewedAdventureComplaint(){
        return adventureComplaintRepository.getAllNotReviewedAdventureComplaint();
    }

    public AdventureComplaint findAdventureComplaintById(long id){
        return adventureComplaintRepository.findById(id).orElse(null);
    }

    public boolean reviewComplaint(ReviewComplaintDto dto){
        if(dto.getComplaintType().equals("AC")){
            return reviewAdventureComplaint(dto);
        }
        else if(dto.getComplaintType().equals("CC")){
            return reviewCottageComplaint(dto);
        }
        else{
            return reviewBoatComplaint(dto);
        }
    }

    private boolean reviewBoatComplaint(ReviewComplaintDto dto) {
        return false;
    }

    private boolean reviewCottageComplaint(ReviewComplaintDto dto) {
        return false;
    }

    private boolean reviewAdventureComplaint(ReviewComplaintDto dto) {
        AdventureComplaint adventureComplaint = findAdventureComplaintById(dto.getComplaintId());
        adventureComplaint.setReviewed(true);
        saveAdventureComplaint(adventureComplaint);
        sendMails(adventureComplaint.getReservation().getCustomer(),
                adventureComplaint.getReservation().getAdventure().getInstructor(),
                dto);
        return true;
    }

    private void sendMails(User customer, User owner, ReviewComplaintDto dto) {
        try {
            emailService.sendAnswerOnComplaint(customer, dto.getMailForCustomer());
            emailService.sendAnswerOnComplaint(owner, dto.getMailForOwner());
        }catch (Exception ignored){ }
    }

    private void saveAdventureComplaint(AdventureComplaint adventureComplaint){
        adventureComplaintRepository.save(adventureComplaint);
    }
}
