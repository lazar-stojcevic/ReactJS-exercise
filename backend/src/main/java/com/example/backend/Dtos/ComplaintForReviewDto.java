package com.example.backend.Dtos;

import com.example.backend.Beans.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ComplaintForReviewDto {
    private long id;
    private String comment;
    private String ownerMail;
    private String customerMail;

    public ComplaintForReviewDto(AdventureReservation adventureReservation){
        this.id = adventureReservation.getComplaint().getId();
        this.comment = adventureReservation.getComplaint().getText();
        this.ownerMail = adventureReservation.getAdventure().getInstructor().getEmail();
        this.customerMail = adventureReservation.getCustomer().getEmail();
    }

    public ComplaintForReviewDto(CottageReservation cottageReservation){
        this.id = cottageReservation.getComplaint().getId();
        this.comment = cottageReservation.getComplaint().getText();
        this.ownerMail = cottageReservation.getCottage().getCottageOwner().getEmail();
        this.customerMail = cottageReservation.getCustomer().getEmail();
    }

    public ComplaintForReviewDto(BoatReservation boatReservation){
        this.id = boatReservation.getComplaint().getId();
        this.comment = boatReservation.getComplaint().getText();
        this.ownerMail = boatReservation.getBoat().getBoatOwner().getEmail();
        this.customerMail = boatReservation.getCustomer().getEmail();
    }
}

