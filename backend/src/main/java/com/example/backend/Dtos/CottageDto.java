package com.example.backend.Dtos;

import com.example.backend.Beans.CottageOwner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CottageDto {
    private long id;
    private long cottageOwnerId;
    private String name;
    private String conductRules;
    private String street;
    private String city;
    private String country;
    private String promo;
    private int price;
}
