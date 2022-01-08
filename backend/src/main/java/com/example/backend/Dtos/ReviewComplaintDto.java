package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReviewComplaintDto {
    private long complaintId;
    private String mailForOwner;
    private String mailForCustomer;
    private String ownerMail;
    private String customerMail;
}
