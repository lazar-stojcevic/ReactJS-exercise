package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.ComplaintForReviewDto;
import com.example.backend.Dtos.NewComplaintDto;
import com.example.backend.Dtos.ReviewComplaintDto;
import com.example.backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ComplaintService {
    @Autowired
    private final AdventureReservationRepository adventureReservationRepository;
    @Autowired
    private final ComplaintRepository complaintRepository;
    @Autowired
    private final CottageReservationRepository cottageReservationRepository;
    @Autowired
    private final BoatReservationRepository boatReservationRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private UserRepository userRepository;

    public ComplaintService(AdventureReservationRepository adventureReservationRepository, ComplaintRepository complaintRepository, CottageReservationRepository cottageReservationRepository, BoatReservationRepository boatReservationRepository) {
        this.adventureReservationRepository = adventureReservationRepository;
        this.complaintRepository = complaintRepository;
        this.cottageReservationRepository = cottageReservationRepository;
        this.boatReservationRepository = boatReservationRepository;
    }

    public boolean SaveAdventureComplaint(NewComplaintDto complaint){
        try {
            AdventureReservation ar = adventureReservationRepository.getById(complaint.getId());
            Complaint ac = new Complaint();
            ac.setText(complaint.getText());
            ac.setReviewed(false);
            //ar.setComplaint(save(ac));
            ar.setComplaint(ac);
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

            //cr.setComplaint(save(cc));
            cr.setComplaint(cc);
            cottageReservationRepository.save(cr);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean SaveBoatComplaint(NewComplaintDto complaint){
        try {
            BoatReservation br = boatReservationRepository.getById(complaint.getId());
            Complaint cc = new Complaint();
            cc.setText(complaint.getText());
            cc.setReviewed(false);

            //cr.setComplaint(save(cc));
            br.setComplaint(cc);
            boatReservationRepository.save(br);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Collection<ComplaintForReviewDto> getAllNotReviewedComplaint(){
        List<ComplaintForReviewDto> list = new ArrayList<>();

        for(AdventureReservation ar : adventureReservationRepository.getAllNotReviewedAdventureComplaint())
            list.add(new ComplaintForReviewDto(ar));

        for(CottageReservation cr : cottageReservationRepository.getAllNotReviewedCottageComplaint())
            list.add(new ComplaintForReviewDto(cr));

        return list;
    }

    /*
    public Complaint findAdventureComplaintById(long id){
        return complaintRepository.findById(id).orElse(null);
    }
    */
    public void reviewComplaint(ReviewComplaintDto dto){
        Complaint complaint = complaintRepository.getById(dto.getComplaintId());
        complaint.setReviewed(true);
        save(complaint);
        sendMails(userRepository.findByEmail(dto.getCustomerMail()),
                userRepository.findByEmail(dto.getOwnerMail()), dto);
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
