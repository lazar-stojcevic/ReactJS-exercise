package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Beans.User;
import com.example.backend.Dtos.UserRegistration;

import java.util.Collection;
import java.util.Optional;

public interface IFishingInstructorService {
    Collection<FishingInstructor> getAllFishingInstructors();
    FishingInstructor saveFishingInstructor(UserRegistration userRegistration) throws Exception;
    FishingInstructor findFishingInstructorById(long id);
    FishingInstructor enableFishingInstructor(long id);
    void disableDisableFishingInstructor(long id);
    void deleteFishingInstructor(long id);
}
