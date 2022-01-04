package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.BoatDto;
import com.example.backend.Repository.AddressRepository;
import com.example.backend.Repository.BoatRepository;
import com.example.backend.Repository.CottageRepository;
import com.example.backend.Services.Interfaces.IBoatService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Optional;

public class BoatService implements IBoatService {

    @Autowired
    private BoatOwnerService boatOwnerService;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private AddressRepository addressRepository;

    public BoatService( BoatRepository boatRepository){
        this.boatRepository = boatRepository;
    }
    @Override
    public Collection<Boat> findAllBoats() {
        return boatRepository.findAll();
    }

    @Override
    public Boat findById(long id) {
        Optional<Boat> boat = boatRepository.findById(id);
        return boat.orElse(null);
    }

    @Override
    public Boat saveBoat(BoatDto newBoat) {
        Boat boat = new Boat();
        boat.setName(newBoat.getName());
        boat.setPromo(newBoat.getPromo());
        boat.setCapacity(newBoat.getCapacity());
        boat.setConductRules(newBoat.getConductRules());
        boat.setAddress(new Address());
        boat.getAddress().setCity(newBoat.getCity());
        boat.getAddress().setStreet(newBoat.getStreet());
        boat.getAddress().setCountry(newBoat.getCountry());
        boat.setType(newBoat.getType());
        boat.setNumberOfEngines(newBoat.getNumberOfEngines());
        boat.setCapacity(newBoat.getCapacity());
        boat.setEnginePower(newBoat.getEnginePower());
        boat.setTopSpeed(newBoat.getTopSpeed());
        boat.setBoatOwner(boatOwnerService.findBoatOwner(newBoat.getBoatOwnerId()));
        BoatPriceList priceList = new BoatPriceList();
        priceList.setPrice(newBoat.getPrice());
        boat.setPriceList(priceList);
        return boatRepository.save(boat);
    }

    @Override
    public Boat updateBoat(BoatDto changeDto) {
        Boat boat = findById(changeDto.getId());
        boat.setName(changeDto.getName());
        boat.setPromo(changeDto.getPromo());
        boat.setCapacity(changeDto.getCapacity());
        boat.setConductRules(changeDto.getConductRules());
        boat.getAddress().setCity(changeDto.getCity());
        boat.getAddress().setStreet(changeDto.getStreet());
        boat.getAddress().setCountry(changeDto.getCountry());
        boat.setType(changeDto.getType());
        boat.setNumberOfEngines(changeDto.getNumberOfEngines());
        boat.setCapacity(changeDto.getCapacity());
        boat.setEnginePower(changeDto.getEnginePower());
        boat.setTopSpeed(changeDto.getTopSpeed());
        boat.getPriceList().setPrice(changeDto.getPrice());
        return boatRepository.save(boat);
    }

    @Override
    public void deleteBoat(long id) {
        boatRepository.deleteById(id);
    }
}
