package com.example.backend.Services;

import com.example.backend.Beans.*;
import com.example.backend.Dtos.CancelTermDto;
import com.example.backend.Dtos.CustomerReserveCottageDto;
import com.example.backend.Dtos.FastReservationDto;
import com.example.backend.Dtos.ReservationSearchDto;
import com.example.backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BoatReservationService {
    @Autowired
    private final BoatReservationRepository boatReservationRepository;


    @Autowired
    private final ForbiddenCustomerToBoatRepository forbiddenCustomerToBoatRepository;

    @Autowired
    private BoatService boatService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    @Autowired
    private AdditionalBoatServiceService additionalBoatServiceService;

    public BoatReservationService(BoatReservationRepository repository, ForbiddenCustomerToBoatRepository forbiddenCustomerToBoatRepository){
        this.boatReservationRepository = repository;
        this.forbiddenCustomerToBoatRepository = forbiddenCustomerToBoatRepository;
    }

    public BoatReservation getCurrentReservationOfInstructor(long boatId){
        Boat boat = this.boatService.findById(boatId);
        for(BoatReservation reservation : boat.getReservations()) {
            if (reservation.getReservationStart().isBefore(LocalDateTime.now()) && reservation.getReservationEnd().isAfter(LocalDateTime.now()))
                return reservation;
        }
        return null;
    }

    public BoatReservation getBoatReservationById(long id){
        return findBoatReservationById(id);
    }

    public Collection<BoatReservation> getAllBoatReservations(){
        return boatReservationRepository.findAll();
    }

    public BoatReservation findBoatReservationById(long id){
        return boatReservationRepository.findById(id).orElse(null);
    }

    public Collection<Boat> getAllAvailableBoatsForSearch(ReservationSearchDto search){
        List<Boat> boats = new ArrayList<>();
        for (Boat boat : boatService.findAllBoats()){
            if (isSearchInBoatAvailablePeriod(search.getDateFrom(), search.getDateTo(), boat) &&
                    search.getPersons()<boat.getCapacity() && isUserNotForbidden(search, boat)
                    && IsInCountry(boat, search.getCountry())
                    && IsInLocation(boat, search.getCity())){
                boolean valid = true;
                for (BoatReservation br: boat.getReservations()){
                    if(isReservationsOverlapForSearch(br, search.getDateFrom(), search.getDateTo())) {
                        valid = false;
                        break;
                    }
                }
                if (valid)
                    boats.add(boat);
            }
        }
        return boats;
    }

    public BoatReservation makeNewAppointment(CustomerReserveCottageDto reservation) throws InterruptedException {
        Customer customer = customerService.findCustomerById(reservation.getUserId());

        BoatReservation boatReservation = new BoatReservation();
        boatReservation.setReservationStart(reservation.getFrom());
        boatReservation.setReservationEnd(reservation.getTo());
        boatReservation.setCustomer(customer);
        boatReservation.setBoat(boatService.findById(reservation.getCottageId()));
        boatReservation.setFast(false);
        if (IsCustomersReservationsOverlapsWithNew(customer, boatReservation)){
            return null;
        }

        boatReservation.setDiscount(0);
        boatReservation.setBoatPriceList(boatReservation.getBoat().getPriceList()); //DA LI MENI OVO TREBA??
        List<AdditionalBoatService> services = findAllSelectedAdditionalServices(reservation.getServices());

        calculateFullPriceOfReservation(boatReservation, services);
        boatReservation.setLength((int) ChronoUnit.DAYS.between(reservation.getFrom(), reservation.getTo()));

        emailService.sendBoatReservationConfirm(customer, boatReservation);
        return save(boatReservation);
    }

    public Collection<BoatReservation> getAllFutureTermsByCustomerId(long id){
        return boatReservationRepository.getAllReservationOfCustomerInFuture(id,
                LocalDateTime.now());
    }

    public Collection<BoatReservation> getAllPastTermsByCustomerId(long id){
        return boatReservationRepository.getAllReservationOfCustomerInPast(id, LocalDateTime.now());
    }

    public Collection<BoatReservation> getAllPastTermsWithoutComplaintByCustomerId(long id){
        return boatReservationRepository.getAllReservationOfCustomerInPastWithoutComplaint(id,
                LocalDateTime.now());
    }

    public BoatReservation cancelTerm(CancelTermDto data){
        Customer customer = this.customerService.findCustomerById(data.getUserId());
        BoatReservation reservation = findBoatReservationById(data.getReservationId());

        ForbiddenCustomerToBoat forbidden = new ForbiddenCustomerToBoat();
        forbidden.setBoat(reservation.getBoat());
        forbidden.setCustomer(customer);
        forbidden.setReservationStart(reservation.getReservationStart());
        forbidden.setReservationEnd(reservation.getReservationEnd());

        forbiddenCustomerToBoatRepository.save(forbidden);
        if (!reservation.isFast())
            reservation.setBoat(null);
        reservation.setCustomer(null);
        save(reservation);
        return reservation;
    }

    public BoatReservation makeFastReservationSlot(FastReservationDto reservation) throws InterruptedException {

        BoatReservation boatReservation= new BoatReservation();
        boatReservation.setReservationStart(reservation.getDate1());
        boatReservation.setReservationEnd(reservation.getDate2());
        boatReservation.setBoat(boatService.findById(reservation.getCottageId()));
        boatReservation.setDiscount(reservation.getSale());
        boatReservation.setBoatPriceList(boatReservation.getBoat().getPriceList());
        boatReservation.setFast(true);

        calculateFullPriceOfReservation(boatReservation, boatReservation.getBoatPriceList().getAdditionalServices());
        boatReservation.setLength((int) ChronoUnit.DAYS.between(reservation.getDate1(), reservation.getDate2()));

        if(isFastReservationPeriodValid(boatReservation)){
            for (Customer cr: customerService.getAllCustomers()) {
                if(cr.getEmail().equals("yoxy99@gmail.com")) {
                    emailService.sendNotificationForCreatingFastReservation(cr, reservation, boatReservation.getBoat().getName());
                }
            }
            return save(boatReservation);
        }

        return null;
    }

    public Collection<BoatReservation> getAllFastReservations(){
        List<BoatReservation> fastReservations = new ArrayList<BoatReservation>();
        for (BoatReservation reservation: getAllBoatReservations()) {
            if(reservation.isFast() && reservation.getCustomer() == null){
                fastReservations.add(reservation);
            }
        }
        return fastReservations;
    }

    //TODO URADI SUTRA
    public BoatReservation reserveFastReservation(long usedId, long reservationId) throws InterruptedException {
        BoatReservation boatReservation = findBoatReservationById(reservationId);
        Customer customer = customerService.findCustomerById(usedId);
        if (IsCustomersReservationsOverlapsWithNew(customer, boatReservation)){
            return null;
        }
        ReservationSearchDto pom = new ReservationSearchDto();
        pom.setDateFrom(boatReservation.getReservationStart());
        pom.setDateTo(boatReservation.getReservationEnd());
        pom.setId(customer.getId());
        if(!isUserNotForbidden(pom, boatReservation.getBoat())) {
            return null;
        }

        boatReservation.setCustomer(customer);
        emailService.sendBoatReservationConfirm(customer, boatReservation);
        return save(boatReservation);
    }

    private boolean IsInLocation(Boat boat, String city) {
        if (city == null)
            return true;
        return boat.getAddress().getCity().toUpperCase(Locale.ROOT).contains(city.toUpperCase(Locale.ROOT));
    }

    private boolean IsInCountry(Boat boat, String country) {
        if (country == null)
            return true;
        return boat.getAddress().getCountry().toUpperCase(Locale.ROOT).contains(country.toUpperCase(Locale.ROOT));
    }

    private BoatReservation save(BoatReservation boatReservation){
        return this.boatReservationRepository.save(boatReservation);
    }

    private boolean isReservationsOverlapForSearch(BoatReservation existingReservation, LocalDateTime start, LocalDateTime end){
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

    private boolean isSearchInBoatAvailablePeriod(LocalDateTime start, LocalDateTime end, Boat boat){
        for(AvailablePeriodBoat periodBoat: boat.getPeriods()){
            if (start.isAfter(periodBoat.getFromDate()) && end.isBefore(periodBoat.getToDate())) {

                return true;
            }
        }
        return false;
    }

    private boolean IsCustomersReservationsOverlapsWithNew(Customer customer, BoatReservation newReservation){
        for (AdventureReservation ar : customer.getAdventureReservations()){
            if (isReservationsOverlapWithAdventureReservations(ar, newReservation))
                return true;
        }

        for (CottageReservation cr : customer.getCottageReservations()){
            if (isReservationsOverlapWithCottageReservations(cr, newReservation)){
                return true;
            }
        }

        for (BoatReservation br: customer.getBoatReservations()){
            if (isReservationsOverlapWithBoatReservations(br, newReservation)){
                return true;
            }
        }
        return false;
    }

    private boolean isFastReservationPeriodValid(BoatReservation reservation){
        if(isSearchInBoatAvailablePeriod(reservation.getReservationStart(), reservation.getReservationEnd(), reservation.getBoat())){
            for (BoatReservation cr : reservation.getBoat().getReservations()){
                if (isReservationsOverlapWithBoatReservations(cr, reservation)){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean isReservationsOverlapWithAdventureReservations(
            AdventureReservation existingReservation, BoatReservation newReservation){
        LocalDateTime existingStart = existingReservation.getReservationStart();
        LocalDateTime existingEnd = existingReservation.getReservationStart().plusHours(existingReservation.getLength());
        LocalDateTime newStart = newReservation.getReservationStart();
        LocalDateTime newEnd = newReservation.getReservationEnd();
        return isToRangesOverlaps(existingStart, existingEnd, newStart, newEnd);
    }

    private boolean isReservationsOverlapWithCottageReservations(
            CottageReservation existingReservation, BoatReservation newReservation){
        LocalDateTime existingStart = existingReservation.getReservationStart();
        LocalDateTime existingEnd = existingReservation.getReservationEnd();
        LocalDateTime newStart = newReservation.getReservationStart();
        LocalDateTime newEnd = newReservation.getReservationEnd();
        return isToRangesOverlaps(existingStart, existingEnd, newStart, newEnd);
    }

    private boolean isReservationsOverlapWithBoatReservations(
            BoatReservation existingReservation, BoatReservation newReservation){
        LocalDateTime existingStart = existingReservation.getReservationStart();
        LocalDateTime existingEnd = existingReservation.getReservationEnd();
        LocalDateTime newStart = newReservation.getReservationStart();
        LocalDateTime newEnd = newReservation.getReservationEnd();
        return isToRangesOverlaps(existingStart, existingEnd, newStart, newEnd);
    }

    private boolean isToRangesOverlaps(LocalDateTime existingStart, LocalDateTime existingEnd,
                                       LocalDateTime newStart, LocalDateTime newEnd){
        if(existingStart.isAfter(newStart) && existingStart.isBefore(newEnd))
            return true;
        else if (existingEnd.isAfter(newStart) && existingEnd.isBefore(newEnd))
            return true;
        else if (newStart.isAfter(existingStart) && newStart.isBefore(existingEnd))
            return true;
        else if (newStart.isEqual(existingStart) && newEnd.isEqual(existingEnd))
            return true;
        else return newEnd.isAfter(existingStart) && newEnd.isBefore(existingEnd);
    }

    private List<AdditionalBoatService> findAllSelectedAdditionalServices(List<Long> ids){
        List<AdditionalBoatService> services = new ArrayList<>();
        for(long id : ids)
            services.add(this.additionalBoatServiceService.findAdditionalServiceById(id));
        return services;
    }

    private void calculateFullPriceOfReservation(
            BoatReservation reservation, List<AdditionalBoatService> addServices){
        int price = reservation.getBoatPriceList().getPrice();
        for(AdditionalBoatService as : addServices){
            price += as.getAddPrice();
            reservation.addAdditionalService(as);
        }
        long days = ChronoUnit.DAYS.between(reservation.getReservationStart(), reservation.getReservationEnd());
        price = price*(int)days;
        reservation.setPrice((int) (price * (1 - reservation.getDiscount()/100)));
    }

    private void calculateFullPriceOfReservation(
            BoatReservation reservation, Set<AdditionalBoatService> addServices){
        int price = reservation.getBoatPriceList().getPrice();
        for(AdditionalBoatService as : addServices){
            price += as.getAddPrice();
            reservation.addAdditionalService(as);
        }
        long days = ChronoUnit.DAYS.between(reservation.getReservationStart(), reservation.getReservationEnd());
        price = price*(int)days;
        reservation.setPrice((int) (price * (1 - reservation.getDiscount()/100)));
    }


    private boolean isUserNotForbidden(ReservationSearchDto searchDto, Boat boat){
        for (ForbiddenCustomerToBoat fc : forbiddenCustomerToBoatRepository.getAllCancellationsOfCustomerToBoat(searchDto.getId(), boat.getId())){
            if (isToRangesOverlaps(fc.getReservationStart(), fc.getReservationEnd(), searchDto.getDateFrom(), searchDto.getDateTo())){
                return false;
            }
        }
        return true;
    }

    public BoatReservation markReservationAsEvaluated(long reservationId){
        BoatReservation boatReservation = findBoatReservationById(reservationId);
        boatReservation.setRated(true);
        return save(boatReservation);
    }

    public Collection<BoatReservation> getAllNextActionsOfBoat(long id) {
        return boatReservationRepository.getAllFreeFutureActionsOfBoat(id, LocalDateTime.now());
    }
}
