package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FishingInstructorChangeDto{
    private long id;
    private String firstname;
    private String lastName;
    private String phone;
    private String street;
    private String city;
    private String country;
}
