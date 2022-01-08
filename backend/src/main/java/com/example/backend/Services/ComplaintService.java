package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.NewComplaintDto;
import com.example.backend.Dtos.ReviewComplaintDto;
import com.example.backend.Repository.ComplaintRepository;
import com.example.backend.Repository.AdventureReservationRepository;
import com.example.backend.Repository.CottageReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ComplaintService {
    @Autowired
    private final AdventureReservationRepository adventureReservationRepository;
    @Autowired
    private final ComplaintRepository complaintRepository;
    @Autowired
    private final CottageReservationRepository cottageReservationRepository;
    @Autowired
    private EmailService emailService;

    public ComplaintService(AdventureReservationRepository adventureReservationRepository, ComplaintRepository complaintRepository, CottageReservationRepository cottageReservationRepository) {
        this.adventureReservationRepository = adventureReservationRepository;
        this.complaintRepository = complaintRepository;
        this.cottageReservationRepository = cottageReservationRepository;
    }

    public boolean SaveAdventureComplaint(NewComplaintDto complaint){
        try {
            AdventureReservation ar = adventureReservationRepository.getById(complaint.getId());
            Complaint ac = new Complaint();
            ac.setText(complaint.getText());
            ac.setReviewed(false);
            ar.setComplaint(save(ac));

            adventureReservationRepository.save(ar);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean SaveCottageComplaint(NewComplaintDto complaint){
        try {
            CottageReservation cr = cottageReservationRepository.getById(complaint.getId());
            Complaint cc = new Complaint();
            cc.setText(complaint.getText());
            cc.setReviewed(false);

            cr.setComplaint(save(cc));
            cottageReservationRepository.save(cr);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Collection<Complaint> getAllNotReviewedAdventureComplaint(){
        return complaintRepository.getAllNotReviewedAdventureComplaint();
    }

    public Complaint findAdventureComplaintById(long id){
        return complaintRepository.findById(id).orElse(null);
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
        Complaint complaint = findAdventureComplaintById(dto.getComplaintId());
        complaint.setReviewed(true);
        save(complaint);
        /*
        sendMails(complaint.getReservation().getCustomer(),
                complaint.getReservation().getAdventure().getInstructor(),
                dto);

         */
        return true;
    }

    private void sendMails(User customer, User owner, ReviewComplaintDto dto) {
        try {
            emailService.sendAnswerOnComplaint(customer, dto.getMailForCustomer());
            emailService.sendAnswerOnComplaint(owner, dto.getMailForOwner());
        }catch (Exception ignored){ }
    }

    private Complaint save(Complaint complaint){
        return complaintRepository.save(complaint);
    }
}
