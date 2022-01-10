package com.example.backend.Services;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.CottageReservation;
import com.example.backend.Beans.Tax;
import com.example.backend.Dtos.SystemIncomeDto;
import com.example.backend.Dtos.SystemIncomeForMail;
import com.example.backend.Repository.AdventureReservationRepository;
import com.example.backend.Repository.CottageReservationRepository;
import com.example.backend.Repository.TaxRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class TaxService {
    @Autowired
    private final TaxRepository taxRepository;
    @Autowired
    private AdventureReservationRepository adventureReservationRepository;
    @Autowired
    private CottageReservationRepository cottageReservationRepository;

    public TaxService(TaxRepository taxRepository){
        this.taxRepository = taxRepository;
    }

    public Tax getCurrentTax(){
        return taxRepository.getCurrentTax();
    }

    public Tax changeTax(double taxRate){
        Tax currentTax = getCurrentTax();
        if(currentTax != null){
            currentTax.setCurrent(false);
            currentTax.setEndTime(LocalDateTime.now());
            save(currentTax);
        }
        Tax newTax = new Tax();
        newTax.setTaxRate(taxRate);
        newTax.setStartTime(LocalDateTime.now());
        return save(newTax);
    }

    private Tax save(Tax tax){
        return taxRepository.save(tax);
    }

    public SystemIncomeForMail calculateIncome(SystemIncomeDto dto) {
        SystemIncomeForMail val = new SystemIncomeForMail();
        for(Tax t : prepareTaxes()){
            //ADVENTURE RESERVATIONS
            for(AdventureReservation ar : adventureReservationRepository
                    .getAllReservationsForCalculatingIncome(dto.getStartTime(), dto.getEndTime()))
                calculateSingleAdventureReservation(t, ar, val);

            //COTTAGE RESERVATIONS
            for(CottageReservation cr : cottageReservationRepository
                    .getAllReservationsForCalculatingIncome(dto.getStartTime(), dto.getEndTime()))
                calculateSingleCottageReservation(t, cr, val);

        }
        return val;
    }

    private void calculateSingleCottageReservation(Tax t, CottageReservation cr, SystemIncomeForMail val) {
        if(isBetween(t, cr.getReservationStart())){
            val.incrementCottage();
            val.incrementIncome(cr.getPrice() * t.getTaxRate() / 100);
        }
    }

    private void calculateSingleAdventureReservation(Tax t, AdventureReservation ar, SystemIncomeForMail val) {
        if(isBetween(t, ar.getReservationStart())){
            val.incrementAdventure();
            val.incrementIncome(ar.getPrice() * t.getTaxRate() / 100);
        }
    }

    private boolean isBetween(Tax t, LocalDateTime reservationStart){
        return t.getStartTime().isBefore(reservationStart) && t.getEndTime().isAfter(reservationStart);
    }

    private List<Tax> prepareTaxes() {
        List<Tax> taxes = new ArrayList<>();
        for(Tax t : taxRepository.findAll()){
            if(t.isCurrent())
                t.setEndTime(LocalDateTime.now());
            taxes.add(t);
        }
        return taxes;
    }
}
