package com.example.backend.Services;

import com.example.backend.Beans.BoatOwner;
import com.example.backend.Beans.Cottage;
import com.example.backend.Dtos.CottageDto;
import com.example.backend.Repository.AddressRepository;
import com.example.backend.Repository.BoatOwnerRepository;
import com.example.backend.Repository.CottageRepository;
import com.example.backend.Services.Interfaces.ICottageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

public class CottageService implements ICottageService {

    @Autowired
    private CottageOwnerService cottageOwnerService;

    @Autowired
    private CottageRepository cottageRepository;

    @Autowired
    private AddressRepository addressRepository;

    public CottageService(CottageRepository cottageRepository){
        this.cottageRepository = cottageRepository;
    }

    @Override
    public Collection<Cottage> findAllCottages() {
        return cottageRepository.findAll();
    }

    @Override
    public Cottage findById(long id) {
        Optional<Cottage> cottage = cottageRepository.findById(id);
        return cottage.orElse(null);
    }

    @Override
    public Cottage saveCottage(CottageDto newCottage) {
        Cottage cottage = new Cottage();
        cottage.setName(newCottage.getName());
        cottage.getAddress().setCity(newCottage.getCity());
        cottage.getAddress().setStreet(newCottage.getStreet());
        cottage.getAddress().setCountry(newCottage.getCountry());
        cottage.setConductRules(newCottage.getConductRules());
        cottage.setCottageOwner(cottageOwnerService.findCottageOwner(newCottage.getId()));
        return cottageRepository.save(cottage);
    }

    @Override
    public Cottage updateCottage(CottageDto changeDto) {
        Cottage cottage = findById(changeDto.getCottageOwnerId());
        cottage.setName(changeDto.getName());
        cottage.getAddress().setCity(changeDto.getCity());
        cottage.getAddress().setStreet(changeDto.getStreet());
        cottage.getAddress().setCountry(changeDto.getCountry());
        cottage.setConductRules(changeDto.getConductRules());
        cottage.setCottageOwner(cottageOwnerService.findCottageOwner(changeDto.getId()));
        return cottageRepository.save(cottage);
    }

    @Override
    public void deleteCottage(long id) {
        cottageRepository.deleteById(id);
    }
}
