package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SystemIncomeForMail {
    private int numberOfAdventureReservations = 0;
    private int numberOfCottageReservations = 0;
    private int numberOfBoatReservations = 0;
    private double income = 0;

    public void incrementAdventure(){
        ++this.numberOfAdventureReservations;
    }

    public void incrementCottage(){
        ++this.numberOfCottageReservations;
    }

    public void incrementBoat(){
        ++this.numberOfBoatReservations;
    }

    public void incrementIncome(double value){
        this.income += value;
    }
}
