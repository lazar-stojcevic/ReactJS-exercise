package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.CottageOwner;
import com.example.backend.Dtos.CottageOwnerChangeDto;
import com.example.backend.Dtos.UserRegistration;

import java.util.Collection;

public interface ICottageOwnerService {
    Collection<CottageOwner> getAllCottageOwners();
    CottageOwner saveCottageOwner(UserRegistration userRegistration) throws Exception;
    CottageOwner findCottageOwner(long id);
    CottageOwner updateCottageOwner(CottageOwnerChangeDto changeDto);
    CottageOwner enableCottageOwner(long id);
    void disableCottageOwner(long id);
    void deleteCottageOwner(long id);
}
