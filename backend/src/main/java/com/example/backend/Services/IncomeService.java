package com.example.backend.Services;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.User;
import com.example.backend.Dtos.IncomeDto;
import com.example.backend.Repository.AdventureReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class IncomeService {
    @Autowired
    private AdventureReservationRepository adventureReservationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;

    public IncomeService(){ }

    public void prepareForCalculatingInstructorIncome(IncomeDto dto){
        LocalDateTime fromDate = LocalDateTime.of(dto.getFromDate(), LocalTime.MIDNIGHT);
        LocalDateTime toDate = LocalDateTime.of(dto.getToDate(), LocalTime.MIDNIGHT);
        List<AdventureReservation> reservations = (List<AdventureReservation>)
                adventureReservationRepository.findByreservationStartBetween(fromDate, toDate);
        calculateInstructorIncome(reservations, dto.getUserId());
    }

    private void calculateInstructorIncome
            (List<AdventureReservation> reservations, long userId) {
        int income = 0;
        int numOfReservations = 0;
        for (AdventureReservation ar : reservations)
            if(ar.getAdventure().getInstructor().getId() == userId && ar.isReserved()){
                income += ar.getPrice();
                numOfReservations++;
            }
        sendMailToInstructor(income, numOfReservations, userId);
    }

    private void sendMailToInstructor(int income, int numOfReservations, long userId) {
        User user = userService.findUserById(userId);
        try {
            emailService.sendMailToInstructorAboutIncome(user, income, numOfReservations);
        } catch (Exception ignore) { }
    }

}
