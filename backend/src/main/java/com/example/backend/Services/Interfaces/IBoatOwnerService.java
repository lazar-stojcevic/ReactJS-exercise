package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.BoatOwner;
import com.example.backend.Dtos.BoatOwnerChangeDto;
import com.example.backend.Dtos.CottageOwnerChangeDto;
import com.example.backend.Dtos.UserRegistration;

import java.util.Collection;

public interface IBoatOwnerService {
    Collection<BoatOwner> getAllBoatOwners();
    BoatOwner saveBoatOwner(UserRegistration userRegistration) throws Exception;
    BoatOwner findBoatOwner(long id);
    BoatOwner updateBoatOwner(BoatOwnerChangeDto changeDto);
    BoatOwner enableBoatOwner(long id);
    void disableBoatOwner(long id);
    void deleteBoatOwner(long id);
}
