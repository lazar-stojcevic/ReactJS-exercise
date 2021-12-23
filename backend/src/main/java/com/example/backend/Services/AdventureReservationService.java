package com.example.backend.Services;

import com.example.backend.Beans.AdditionalService;
import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.Customer;
import com.example.backend.Beans.FishingInstructor;
import com.example.backend.Dtos.*;
import com.example.backend.Repository.AdventureReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AdventureReservationService {

    @Autowired
    private final AdventureReservationRepository adventureReservationRepository;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private FishingInstructorService fishingInstructorService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdditionalServiceService additionalServiceService;

    public AdventureReservationService(AdventureReservationRepository repository){
        this.adventureReservationRepository = repository;
    }

    public AdventureReservation getAdventureReservationById(long id){
        return findAdventureReservationById(id);
    }

    public Collection<AdventureReservation> getAllAdventureReservations(){
        return adventureReservationRepository.findAll();
    }

    public AdventureReservation makeNewAppointment(CustomerReserveTermDto reservation) throws InterruptedException {
       Customer customer = customerService.findCustomerById(reservation.getUserId());
       AdventureReservation adventureReservation = getAdventureReservationById(reservation.getReservationId());
        for (AdventureReservation ar : customer.getAdventureReservations()){
            if (isReservationsOverlap(ar, adventureReservation))
                return null;
        }
        List<AdditionalService> services = findAllSelectedAdditionalServices(reservation.getServices());
        adventureReservation.setCustomer(customer);
        calculateFullPriceOfReservation(adventureReservation, services);
        emailService.sendAdventureReservationConfirm(customer);
        return save(adventureReservation);
    }

    public Collection<AdventureReservation> getAllNextReservedTermsOfAdventure(long adventureId){
        List<AdventureReservation> adventureReservations = new ArrayList<>();
        for (AdventureReservation ar : adventureReservationRepository.findAll())
            if(ar.isReserved() && ar.getAdventure().getId() == adventureId &&
                    ar.getReservationStart().isAfter(LocalDateTime.now()))
                adventureReservations.add(ar);

        return adventureReservations;
    }

    public Collection<AdventureReservation> getAllFreeFastReservations(long adventureId){
        List<AdventureReservation> reservations = new ArrayList<>();
        for(AdventureReservation ar : adventureService.findAdventureById(adventureId).getReservations())
            if(!ar.isReserved() && ar.getLastDateToReserve().isAfter(LocalDateTime.now()))
                reservations.add(ar);
        return reservations;
    }

    public Collection<AdventureReservation> getAllPastReservationOfInstructor(long instructorId){
        List<AdventureReservation> pastAdventure = new ArrayList<>();
        for (AdventureReservation ar: adventureReservationRepository.findAll()) {
            LocalDateTime reservationEnd = ar.getReservationStart().plusHours(ar.getLength());
            if(reservationEnd.isBefore(LocalDateTime.now()) && ar.isReserved() &&
                    ar.getAdventure().getInstructor().getId() == instructorId){
                pastAdventure.add(ar);
            }
        }
        return pastAdventure;
    }

    public Collection<AdventureReservation> getAllReservationOfCustomerForEvaluation(long customerId){
        return adventureReservationRepository.getAllReservationOfCustomerForEvaluation(customerId,
                LocalDateTime.now());
    }

    public void markReservationAsEvaluated(long reservationId){
        AdventureReservation adventureReservation = findAdventureReservationById(reservationId);
        adventureReservation.setRated(true);
        save(adventureReservation);
    }

    public AdventureReservation findAdventureReservationById(long id){
        return adventureReservationRepository.findById(id).orElse(null);
    }

    public Collection<AdventureReservation> getAllAvailableReservationsForSearch(ReservationSearchDto search){
        List<AdventureReservation> reservations = new ArrayList<>();
        for (AdventureReservation ar: adventureReservationRepository.findByreservationStartBetween(search.getDateFrom(), search.getDateTo())){
            if (!ar.isReserved() && ar.getAdventure().getMaxPersons() >= search.getPersons() &&
                    ar.getLastDateToReserve().isAfter(LocalDateTime.now().plusDays(3)) &&
                    !IsForbiddenToCustomer(ar, search.getId())){
                reservations.add(ar);
            }
        }
        return reservations;
    }

    public AdventureReservation createFreeFastReservation(MakeFastReservationDto dto){
        AdventureReservation adventureReservation = makeAdventureReservation(dto);
        for(AdventureReservation ar : getAllNextReservationsOfInstructor(dto.getInstructorId())){
            if(!isReservationInAvailableTimespanOfInstructor(adventureReservation, dto.getInstructorId()))
                return null;
            if(isReservationsOverlap(ar, adventureReservation))
                return null;
        }
        adventureReservation.setAdventure(adventureService.findAdventureById(dto.getAdventureId()));
        calculateFullPriceOfReservation(adventureReservation, dto.getAddServices());
        sendNotificationMailToAllPrepaidCustomers(dto.getAdventureId());
        return save(adventureReservation);
    }

    public boolean deleteAdventureReservation(long id){
        AdventureReservation adventureReservation = findAdventureReservationById(id);
        if(adventureReservation.isReserved())
            return false;
        adventureReservationRepository.deleteById(id);
        return true;
    }

    public AdventureReservation cancelAdventureReservation(long id){
        AdventureReservation adventureReservation = findAdventureReservationById(id);
        LocalDateTime lastDateForCanceling = adventureReservation.getReservationStart().minusDays(3);
        if(LocalDateTime.now().isAfter(lastDateForCanceling))
            return null;
        adventureReservation.setCustomer(null);
        return save(adventureReservation);
    }

    public AdventureReservation makeReportOfAdventureReservation(long id, String report){
        AdventureReservation adventureReservation = findAdventureReservationById(id);
        adventureReservation.setReport(report);
        return save(adventureReservation);
    }
    //TODO: Rezervacija avanture
    public AdventureReservation fastReserveAdventure(ReserveAdventureDto dto){
        return save(prepareReservationForSaving(dto));
    }

    public Collection<AdventureReservation> getAllFutureTermsByCustomerId(long id){
        return adventureReservationRepository.getAllReservationOfCustomerInFuture(id,
                LocalDateTime.now());
    }

    public AdventureReservation cancelTerm(CancelTermDto data){
        Customer customer = this.customerService.findCustomerById(data.getUserId());
        AdventureReservation reservation = adventureReservationRepository.getById(data.getReservationId());
        reservation.getForbidenCustomers().add(customer);
        reservation.setCustomer(null);
        return adventureReservationRepository.save(reservation);
    }

    private AdventureReservation prepareReservationForSaving(ReserveAdventureDto dto){
        AdventureReservation reservation = findAdventureReservationById(dto.getAdventureReservationId());
        Customer customer = this.customerService.findCustomerById(dto.getCustomerId());
        List<AdditionalService> services = findAllSelectedAdditionalServices(dto.getSelectedAdditionalServicesIds());
        reservation.setCustomer(customer);
        calculateFullPriceOfReservation(reservation, services);
        return reservation;
    }

    private List<AdditionalService> findAllSelectedAdditionalServices(List<Long> ids){
        List<AdditionalService> services = new ArrayList<>();
        for(long id : ids)
            services.add(this.additionalServiceService.findAdditionalServiceById(id));
        return services;
    }

    private Collection<AdventureReservation> getAllNextReservationsOfInstructor(long instructorId){
        List<AdventureReservation> allNextReservations = new ArrayList<>();
        for(AdventureReservation ar : getAllAdventureReservations())
            if(ar.getAdventure().getInstructor().getId() == instructorId &&
                    ar.getReservationStart().isAfter(LocalDateTime.now()))
                allNextReservations.add(ar);

        return allNextReservations;
    }

    //METODA PROVERAVA DA LI SE DVA TERMINA PREKLAPAJU
    private boolean isReservationsOverlap(
            AdventureReservation existingReservation, AdventureReservation newReservation){
        LocalDateTime existingReservationEndTime = existingReservation.getReservationStart()
                .plusHours(existingReservation.getLength());
        LocalDateTime newReservationEndTime = newReservation.getReservationStart()
                .plusHours(newReservation.getLength());
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

    private boolean isReservationInAvailableTimespanOfInstructor(
            AdventureReservation adventureReservation, long instructorId) {
        LocalDateTime reservationEnd = adventureReservation.getReservationStart()
                .plusHours(adventureReservation.getLength());
        FishingInstructor instructor = fishingInstructorService.findFishingInstructorById(instructorId);

        return adventureReservation.getReservationStart().isAfter(instructor.getAvailable().getFromDate()) &&
                reservationEnd.isBefore(instructor.getAvailable().getToDate());
    }

    private AdventureReservation makeAdventureReservation(MakeFastReservationDto dto){
        AdventureReservation adventureReservation = new AdventureReservation();
        adventureReservation.setReservationStart(dto.getAdventureStart());
        adventureReservation.setLastDateToReserve(dto.getLastDateToReserve());
        adventureReservation.setLength(dto.getLength());
        return adventureReservation;
    }

    private void sendNotificationMailToAllPrepaidCustomers(long adventureId){
        for(Customer customer : adventureService.findAdventureById(adventureId).getPrepaidCustomers()){
            try {
                emailService.sendNotificationForCreatingNewAdventureReservation(customer);
            }catch (Exception e){System.out.println(e.toString());}
        }
    }

    private AdventureReservation save(AdventureReservation adventureReservation){
        return this.adventureReservationRepository.save(adventureReservation);
    }


    //PROVERA DA LI JE ZABRANJENO KORISNIKU DA ZAKAZUJE PONOVO OVAJ TERMIN
    private boolean IsForbiddenToCustomer(AdventureReservation ar, long id){
        Customer customer = customerService.findCustomerById(id);
        if (ar.getForbidenCustomers().contains(customer))
            return true;
        return false;
    }

    //TODO: MENJANO NAKNADNO U SLUCAJU BAGOVA
    private void calculateFullPriceOfReservation(
            AdventureReservation reservation, List<AdditionalService> addServices){
        int price = reservation.getAdventure().getPriceList().getPrice();
        for(AdditionalService as : addServices){
            price += as.getAddPrice();
            reservation.addAdditionalService(as);
        }
        reservation.setPrice(price);
    }
}
