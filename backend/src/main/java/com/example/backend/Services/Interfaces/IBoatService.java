package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.Boat;
import com.example.backend.Beans.Cottage;
import com.example.backend.Dtos.BoatDto;
import com.example.backend.Dtos.CottageDto;

import java.util.Collection;

public interface IBoatService {
    Collection<Boat> findAllBoats();
    Boat findById(long id);
    Boat saveBoat(BoatDto newBoat);
    Boat updateBoat(BoatDto changeDto);
}
