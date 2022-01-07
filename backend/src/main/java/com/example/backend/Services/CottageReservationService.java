package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.CancelTermDto;
import com.example.backend.Dtos.CustomerReserveCottageDto;
import com.example.backend.Dtos.FastReservationDto;
import com.example.backend.Dtos.ReservationSearchDto;
import com.example.backend.Repository.CottageRepository;
import com.example.backend.Repository.CottageReservationRepository;
import com.example.backend.Repository.ForbiddenCustomerToCottageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class CottageReservationService {

    @Autowired
    private final CottageReservationRepository cottageReservationRepository;

    @Autowired
    private final CottageRepository cottageRepository;

    @Autowired
    private final ForbiddenCustomerToCottageRepository forbiddenCustomerToCottageRepository;

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

    public CottageReservationService(CottageReservationRepository repository, CottageRepository cottageRepository, ForbiddenCustomerToCottageRepository forbiddenCustomerToCottageRepository){
        this.cottageReservationRepository = repository;
        this.cottageRepository = cottageRepository;
        this.forbiddenCustomerToCottageRepository = forbiddenCustomerToCottageRepository;
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
                    isCottageHaveEnoughBeds(search.getPersons(), cottage) && isUserNotForbidden(search, cottage)){
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
        cottageReservation.setFast(false);
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

    public Collection<CottageReservation> getAllFutureTermsByCustomerId(long id){
        return cottageReservationRepository.getAllReservationOfCustomerInFuture(id,
                LocalDateTime.now());
    }

    public Collection<CottageReservation> getAllPastTermsByCustomerId(long id){
        return cottageReservationRepository.getAllReservationOfCustomerInPast(id, LocalDateTime.now());
    }

    public Collection<CottageReservation> getAllPastTermsWithoutComplaintByCustomerId(long id){
        return cottageReservationRepository.getAllReservationOfCustomerInPastWithoutComplaint(id,
                LocalDateTime.now());
    }

    public CottageReservation cancelTerm(CancelTermDto data){
        Customer customer = this.customerService.findCustomerById(data.getUserId());
        CottageReservation reservation = findCottageReservationById(data.getReservationId());

        ForbiddenCustomerToCottage forbidden = new ForbiddenCustomerToCottage();
        forbidden.setCottage(reservation.getCottage());
        forbidden.setCustomer(customer);
        forbidden.setReservationStart(reservation.getReservationStart());
        forbidden.setReservationEnd(reservation.getReservationEnd());

        forbiddenCustomerToCottageRepository.save(forbidden);
        reservation.setCottage(null);
        reservation.setCustomer(null);
        save(reservation);
        //TODO
        //cottageReservationRepository.delete(cottageReservationRepository.getById(reservation.getId()));
        return reservation;
    }

    public CottageReservation makeFastReservationSlot(FastReservationDto reservation) throws InterruptedException {

        CottageReservation cottageReservation = new CottageReservation();
        cottageReservation.setReservationStart(reservation.getDate1());
        cottageReservation.setReservationEnd(reservation.getDate2());
        cottageReservation.setCottage(cottageService.findById(reservation.getCottageId()));
        cottageReservation.setDiscount(reservation.getSale());
        cottageReservation.setCottagePriceList(cottageReservation.getCottage().getCottagePriceList());
        cottageReservation.setFast(true);

        calculateFullPriceOfReservation(cottageReservation, cottageReservation.getCottagePriceList().getAdditionalServices());
        cottageReservation.setLength((int) ChronoUnit.DAYS.between(reservation.getDate1(), reservation.getDate2()));

        if(isFastReservationPeriodValid(cottageReservation)){
            for (Customer cr: customerService.getAllCustomers()) {
                if(cr.getEmail().equals("yoxy99@gmail.com")) {
                    emailService.sendNotificationForCreatingFastReservation(cr, reservation, cottageReservation.getCottage().getName());
                }
            }
            return save(cottageReservation);
        }

        return null;
    }

    public Collection<CottageReservation> getAllFastReservations(){
        List<CottageReservation> fastReservations = new ArrayList<CottageReservation>();
        for (CottageReservation reservation: getAllCottageReservations()) {
            if(reservation.isFast() && reservation.getCustomer() == null){
                fastReservations.add(reservation);
            }
        }
        return fastReservations;
    }

    public CottageReservation reserveFastReservation(long usedId, long reservationId){
        CottageReservation cottageReservation = findCottageReservationById(reservationId);
        cottageReservation.setCustomer(customerService.findCustomerById(usedId));
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

    private boolean isFastReservationPeriodValid(CottageReservation reservation){
        if(isSearchInCottageAvailablePeriod(reservation.getReservationStart(), reservation.getReservationEnd(), reservation.getCottage())){
            for (CottageReservation cr : reservation.getCottage().getReservations()){
                if (isReservationsOverlapWithCottageReservations(cr, reservation)){
                    return false;
                }
            }
            return true;
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

    private boolean isToRangesOverlaps(LocalDateTime existingStart, LocalDateTime existingEnd,
                                       LocalDateTime newStart, LocalDateTime newEnd){
        if(newStart.isAfter(existingStart) &&
                newStart.isBefore(existingStart))
            return true;
        else if(newEnd.isAfter(existingStart) &&
                newEnd.isBefore(existingEnd))
            return true;
        else if(newStart.isEqual(existingStart))
            return true;
        else return newStart.isBefore(existingStart) &&
                    newEnd.isAfter(existingEnd);
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
        long days = ChronoUnit.DAYS.between(reservation.getReservationStart(), reservation.getReservationEnd());
        price = price*(int)days;
        reservation.setPrice((int) (price * (1 - reservation.getDiscount()/100)));
    }

    private void calculateFullPriceOfReservation(
            CottageReservation reservation, Set<AdditionalCottageService> addServices){
        int price = reservation.getCottagePriceList().getPrice();
        for(AdditionalCottageService as : addServices){
            price += as.getAddPrice();
            reservation.addAdditionalService(as);
        }
        long days = ChronoUnit.DAYS.between(reservation.getReservationStart(), reservation.getReservationEnd());
        price = price*(int)days;
        reservation.setPrice((int) (price * (1 - reservation.getDiscount()/100)));
    }


    private boolean isUserNotForbidden(ReservationSearchDto searchDto, Cottage cottage){
        Customer customer = customerService.findCustomerById(searchDto.getId());
        for (ForbiddenCustomerToCottage fc : forbiddenCustomerToCottageRepository.getAllCancellationsOfCustomerToCottage(searchDto.getId(), cottage.getId())){
            if (isToRangesOverlaps(fc.getReservationStart(), fc.getReservationEnd(), searchDto.getDateFrom(), searchDto.getDateTo())){
                return false;
            }
        }
        return true;
    }

    public CottageReservation markReservationAsEvaluated(long reservationId){
        CottageReservation cottageReservation = findCottageReservationById(reservationId);
        cottageReservation.setRated(true);
        return save(cottageReservation);
    }
}
