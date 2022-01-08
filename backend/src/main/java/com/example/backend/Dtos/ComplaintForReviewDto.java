package com.example.backend.Dtos;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.CottageReservation;
import com.example.backend.Beans.Customer;
import com.example.backend.Beans.User;
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
}

