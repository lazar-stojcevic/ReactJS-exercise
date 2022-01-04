package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class BoatDto {
    private long id;
    private long boatOwnerId;
    private String name;
    private String type;
    private int numberOfEngines;
    private double enginePower;
    private double topSpeed;
    private int capacity;
    private String conductRules;
    private String street;
    private String city;
    private String country;
    private String promo;
    private int price;
}
