package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.AdditionalServiceDto;
import com.example.backend.Dtos.BoatDto;
import com.example.backend.Dtos.NewSubcriptionDto;
import com.example.backend.Dtos.PasswordChangeDto;
import com.example.backend.Repository.AddressRepository;
import com.example.backend.Repository.BoatRepository;
import com.example.backend.Repository.CottageRepository;
import com.example.backend.Repository.CustomerRepository;
import com.example.backend.Services.Interfaces.IBoatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

@Service
public class BoatService implements IBoatService {

    @Autowired
    private BoatOwnerService boatOwnerService;

    @Autowired
    private BoatRepository boatRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerService customerService;

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

    public boolean isUserSubcribed(long boatId, long userId){
        Boat boat = findById(boatId);
        Customer customer = customerService.findCustomerById(userId);

        return boat.getPrepaidCustomers().contains(customer);
    }

    public void newSubscription(NewSubcriptionDto newSubcription){
        if(isUserSubcribed(newSubcription.getAdvertiserId(), newSubcription.getUserId())){
            return;
        }

        Boat boat = findById(newSubcription.getAdvertiserId());
        Customer customer = customerService.findCustomerById(newSubcription.getUserId());

        boat.getPrepaidCustomers().add(customer);
        boatRepository.save(boat);
    }

    public void unsubscribe(NewSubcriptionDto toUnsubscribe){
        if(!isUserSubcribed(toUnsubscribe.getAdvertiserId(), toUnsubscribe.getUserId())){
            return;
        }

        Boat boat = findById(toUnsubscribe.getAdvertiserId());
        Customer customer = customerService.findCustomerById(toUnsubscribe.getUserId());

        boat.getPrepaidCustomers().remove(customer);
        boatRepository.save(boat);
    }

    public Boat addAdditionalService(AdditionalServiceDto serviceDto){
        Boat boat = findById(serviceDto.getPriceList());
        AdditionalBoatService additionalService45 = new AdditionalBoatService();
        additionalService45.setName(serviceDto.getName());
        additionalService45.setAddPrice(serviceDto.getAddPrice());
        additionalService45.setPriceList(boat.getPriceList());
        boat.getPriceList().getAdditionalServices().add(additionalService45);
        return boatRepository.save(boat);
    }

}
