package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.CustomerReserveCottageDto;
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

    @Autowired
    private AdditionalCottageServiceService additionalCottageServiceService;

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

    public CottageReservation makeNewAppointment(CustomerReserveCottageDto reservation) throws InterruptedException {
        Customer customer = customerService.findCustomerById(reservation.getUserId());

        CottageReservation cottageReservation = new CottageReservation();
        cottageReservation.setReservationStart(reservation.getFrom());
        cottageReservation.setReservationEnd(reservation.getTo());
        cottageReservation.setCustomer(customer);
        cottageReservation.setCottage(cottageService.findById(reservation.getCottageId()));
        if (IsCustomersReservationsOverlapsWithNew(customer, cottageReservation)){
            return null;
        }

        cottageReservation.setDiscount(0);
        cottageReservation.setCottagePriceList(cottageReservation.getCottage().getCottagePriceList()); //DA LI MENI OVO TREBA??
        List<AdditionalCottageService> services = findAllSelectedAdditionalServices(reservation.getServices());

        calculateFullPriceOfReservation(cottageReservation, services);
        cottageReservation.setLength((int) ChronoUnit.DAYS.between(reservation.getFrom(), reservation.getTo()));

        emailService.sendCottageReservationConfirm(customer, cottageReservation);
        return save(cottageReservation);
    }


    private CottageReservation save(CottageReservation cottageReservation){
        return this.cottageReservationRepository.save(cottageReservation);
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

    private boolean IsCustomersReservationsOverlapsWithNew(Customer customer, CottageReservation newReservation){
        for (AdventureReservation ar : customer.getAdventureReservations()){
            if (isReservationsOverlapWithAdventureReservations(ar, newReservation))
                return true;
        }

        for (CottageReservation cr : customer.getCottageReservations()){
            if (isReservationsOverlapWithCottageReservations(cr, newReservation)){
                return true;
            }
        }

        for (CottageReservation cr: newReservation.getCottage().getReservations()){
            if (isReservationsOverlapWithCottageReservations(cr, newReservation)){
                return true;
            }
        }
        return false;
    }

    private boolean isReservationsOverlapWithAdventureReservations(
            AdventureReservation existingReservation, CottageReservation newReservation){
        LocalDateTime existingReservationEndTime = existingReservation.getReservationStart()
                .plusHours(existingReservation.getLength());
        LocalDateTime newReservationEndTime = newReservation.getReservationEnd();
        if(newReservation.getReservationStart().isAfter(existingReservation.getReservationStart()) &&
                newReservation.getReservationStart().isBefore(existingReservationEndTime))
            return true;
        else if(newReservationEndTime.isAfter(existingReservation.getReservationStart()) &&
                newReservationEndTime.isBefore(existingReservationEndTime))
            return true;
        else if(newReservation.getReservationStart().isEqual(existingReservation.getReservationStart()))
            return true;
        else return newReservation.getReservationStart().isBefore(existingReservation.getReservationStart()) &&
                    newReservationEndTime.isAfter(existingReservationEndTime);
    }

    private boolean isReservationsOverlapWithCottageReservations(
            CottageReservation existingReservation, CottageReservation newReservation){
        LocalDateTime existingReservationEndTime = existingReservation.getReservationEnd();
        LocalDateTime newReservationEndTime = newReservation.getReservationEnd();
        if(newReservation.getReservationStart().isAfter(existingReservation.getReservationStart()) &&
                newReservation.getReservationStart().isBefore(existingReservationEndTime))
            return true;
        else if(newReservationEndTime.isAfter(existingReservation.getReservationStart()) &&
                newReservationEndTime.isBefore(existingReservationEndTime))
            return true;
        else if(newReservation.getReservationStart().isEqual(existingReservation.getReservationStart()))
            return true;
        else return newReservation.getReservationStart().isBefore(existingReservation.getReservationStart()) &&
                    newReservationEndTime.isAfter(existingReservationEndTime);
    }

    private List<AdditionalCottageService> findAllSelectedAdditionalServices(List<Long> ids){
        List<AdditionalCottageService> services = new ArrayList<>();
        for(long id : ids)
            services.add(this.additionalCottageServiceService.findAdditionalServiceById(id));
        return services;
    }

    private void calculateFullPriceOfReservation(
            CottageReservation reservation, List<AdditionalCottageService> addServices){
        int price = reservation.getCottagePriceList().getPrice();
        for(AdditionalCottageService as : addServices){
            price += as.getAddPrice();
            reservation.addAdditionalService(as);
        }
        reservation.setPrice((int) (price * (1 - reservation.getDiscount()/100)));
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
