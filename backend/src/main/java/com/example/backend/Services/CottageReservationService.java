package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.ReservationSearchDto;
import com.example.backend.Repository.CottageRepository;
import com.example.backend.Repository.CottageReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class CottageReservationService {

    @Autowired
    private final CottageReservationRepository cottageReservationRepository;

    @Autowired
    private final CottageRepository cottageRepository;

    @Autowired
    private CottageService cottageService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    public CottageReservationService(CottageReservationRepository repository, CottageRepository cottageRepository){
        this.cottageReservationRepository = repository;
        this.cottageRepository = cottageRepository;
    }

    public CottageReservation getCurrentReservationOfInstructor(long cottageId){
        Cottage cottage = this.cottageService.findById(cottageId);
        for(CottageReservation reservation : cottage.getReservations()) {
            if (reservation.getReservationStart().isBefore(LocalDateTime.now()) && reservation.getReservationEnd().isAfter(LocalDateTime.now()))
                return reservation;
        }
        return null;
    }

    public CottageReservation getCottageReservationById(long id){
        return findCottageReservationById(id);
    }

    public Collection<CottageReservation> getAllCottageReservations(){
        return cottageReservationRepository.findAll();
    }

    public CottageReservation findCottageReservationById(long id){
        return cottageReservationRepository.findById(id).orElse(null);
    }

    public Collection<Cottage> getAllAvailableCottagesForSearch(ReservationSearchDto search){
        List<Cottage> cottages = new ArrayList<>();
        for (Cottage cottage : cottageRepository.findAll()){
            if (isSearchInCottageAvailablePeriod(search.getDateFrom(), search.getDateTo(), cottage) &&
                    isCottageHaveEnoughBeds(search.getPersons(), cottage)){
                boolean valid = true;
                for (CottageReservation cr: cottage.getReservations()){
                     if(isReservationsOverlapForSearch(cr, search.getDateFrom(), search.getDateTo())) {
                         valid = false;
                         break;
                     }
                }
                if (valid)
                    cottages.add(cottage);
            }
        }
        return cottages;
    }

    private CottageReservation save(CottageReservation cottageReservation){
        return this.cottageReservationRepository.save(cottageReservation);
    }

    private boolean isReservationsOverlap(CottageReservation existingReservation, CottageReservation newReservation){
        if(newReservation.getReservationStart().isAfter(existingReservation.getReservationStart()) &&
                newReservation.getReservationStart().isBefore(existingReservation.getReservationEnd()))
            return true;
        else if(newReservation.getReservationEnd().isAfter(existingReservation.getReservationStart()) &&
                newReservation.getReservationEnd().isBefore(existingReservation.getReservationEnd()))
            return true;
        else if(newReservation.getReservationStart().isEqual(existingReservation.getReservationStart()))
            return true;
        else return newReservation.getReservationStart().isBefore(existingReservation.getReservationStart()) &&
                    newReservation.getReservationEnd().isAfter(existingReservation.getReservationEnd());
    }

    private boolean isReservationsOverlapForSearch(CottageReservation existingReservation, LocalDateTime start, LocalDateTime end){
        if(start.isAfter(existingReservation.getReservationStart()) &&
                start.isBefore(existingReservation.getReservationEnd()))
            return true;
        else if(end.isAfter(existingReservation.getReservationStart()) &&
                end.isBefore(existingReservation.getReservationEnd()))
            return true;
        else if(start.isEqual(existingReservation.getReservationStart()))
            return true;
        else return start.isBefore(existingReservation.getReservationStart()) &&
                    end.isAfter(existingReservation.getReservationEnd());
    }

    private boolean isReservationInAvailablePeriod(CottageReservation newReservation){
        for (AvailablePeriodCottage periodCottage: cottageService.findById(newReservation.getCottage().getId()).getPeriods()) {
            if(newReservation.getReservationStart().isAfter(periodCottage.getFromDate()) &&
                    newReservation.getReservationEnd().isBefore(periodCottage.getToDate()))
                return true;
        }
        return false;
    }

    private boolean isSearchInCottageAvailablePeriod(LocalDateTime start, LocalDateTime end, Cottage cottage){
         for(AvailablePeriodCottage periodCottage: cottage.getPeriods()){
             if (start.isAfter(periodCottage.getFromDate()) && end.isBefore(periodCottage.getToDate())) {

                 return true;
             }
         }
         return false;
    }

    private boolean isCottageHaveEnoughBeds(int numberOfBeds, Cottage cottage){
        int beds = 0;
        for (Room room : cottage.getRooms())
            beds += room.getNumberOfBeds();
        return beds >= numberOfBeds;
    }

    private boolean ReservationTimeCheck(CottageReservation cottageReservation){
        if (!isReservationInAvailablePeriod(cottageReservation)){
            return false;
        }else{
            for (CottageReservation reservation: cottageService.findById(cottageReservation.getCottage().getId()).getReservations()) {
                if(!isReservationsOverlap(cottageReservation, reservation)){
                    return false;
                }
            }
        }
        return true;
    }

    public double CountPrice(CottageReservation cottageReservation){
        long days = ChronoUnit.DAYS.between(cottageReservation.getReservationStart(), cottageReservation.getReservationEnd());
        double price = cottageReservation.getCottagePriceList().getPrice();
        for (AdditionalCottageService service: cottageReservation.getCottagePriceList().getAdditionalServices()){
            price = price + service.getAddPrice();
        }
        return  days*price;
    }

}
