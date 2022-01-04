package com.example.backend.Services.Interfaces;

import com.example.backend.Beans.AdditionalBoatService;
import com.example.backend.Dtos.AdditionalServiceDto;

public interface IAdditionalBoatService {
    AdditionalBoatService findAdditionalServiceById(long id);
    AdditionalBoatService createBoatService(AdditionalServiceDto serviceDto);
}
