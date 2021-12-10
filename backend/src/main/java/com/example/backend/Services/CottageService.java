package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.CottageDto;
import com.example.backend.Dtos.RoomDto;
import com.example.backend.Repository.AddressRepository;
import com.example.backend.Repository.BoatOwnerRepository;
import com.example.backend.Repository.CottageRepository;
import com.example.backend.Services.Interfaces.ICottageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
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
        cottage.setAddress(new Address());
        cottage.getAddress().setCity(newCottage.getCity());
        cottage.getAddress().setStreet(newCottage.getStreet());
        cottage.getAddress().setCountry(newCottage.getCountry());
        cottage.setConductRules(newCottage.getConductRules());
        cottage.setPromo(newCottage.getPromo());
        CottageOwner owner = cottageOwnerService.findCottageOwner(newCottage.getCottageOwnerId());
        cottage.setCottageOwner(owner);
        PriceList priceList = new PriceList();
        priceList.setPrice(newCottage.getPrice());
        cottage.setPriceList(priceList);
        return cottageRepository.save(cottage);
    }

    @Override
    public Cottage updateCottage(CottageDto changeDto) {
        Cottage cottage = findById(changeDto.getId());
        cottage.setName(changeDto.getName());
        cottage.getAddress().setCity(changeDto.getCity());
        cottage.getAddress().setStreet(changeDto.getStreet());
        cottage.getAddress().setCountry(changeDto.getCountry());
        cottage.setPromo(changeDto.getPromo());
        cottage.setConductRules(changeDto.getConductRules());
        cottage.setCottageOwner(cottageOwnerService.findCottageOwner(changeDto.getId()));
        cottage.getPriceList().setPrice(changeDto.getPrice());
        return cottageRepository.save(cottage);
    }

    @Override
    public void deleteCottage(long id) {
        cottageRepository.deleteById(id);
    }
}
