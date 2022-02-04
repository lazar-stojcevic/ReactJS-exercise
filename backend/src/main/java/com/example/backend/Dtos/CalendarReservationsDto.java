package com.example.backend.Dtos;

import com.example.backend.Beans.AdventureReservation;
import com.example.backend.Beans.BoatReservation;
import com.example.backend.Beans.CottageReservation;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class CalendarReservationsDto {
    private long id;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime start;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime end;
    private String title;

    public CalendarReservationsDto(AdventureReservation ar){
        this.id = ar.getId();
        this.start = ar.getReservationStart();
        this.end = ar.getReservationStart().plusHours(ar.getLength()).plusMinutes(ar.getLengthMin());
        if(ar.getCustomer() != null)
            this.title ="CUSTOMER: " + ar.getCustomer().getEmail() + "\n" + "PRICE: " + ar.getPrice() + " RSD\n"
                    + "REPORTED: " + ar.isReported();
        else this.title = "NOT RESERVED";
    }

    public CalendarReservationsDto(CottageReservation cr){
        this.id = cr.getId();
        this.start = cr.getReservationStart();
        this.end = cr.getReservationEnd();
        if(cr.getCustomer() != null)
            this.title ="CUSTOMER: " + cr.getCustomer().getEmail() + "\n" + "PRICE: " + cr.getPrice() + " RSD\n"
                    + "REPORTED: " + cr.isReported();
        else this.title = "NOT RESERVED";
    }

    public CalendarReservationsDto(BoatReservation br){
        this.id = br.getId();
        this.start = br.getReservationStart();
        this.end = br.getReservationEnd();
        if(br.getCustomer() != null)
            this.title ="CUSTOMER: " + br.getCustomer().getEmail() + "\n" + "PRICE: " + br.getPrice() + " RSD\n"
                    + "REPORTED: " + br.isReported();
        else this.title = "NOT RESERVED";
    }
}
