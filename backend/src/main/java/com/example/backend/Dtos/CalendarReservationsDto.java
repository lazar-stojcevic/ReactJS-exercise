package com.example.backend.Dtos;

import com.example.backend.Beans.AdventureReservation;
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
        this.end = ar.getReservationStart().plusHours(ar.getLength());
        if(ar.getCustomer() != null)
            this.title ="CUSTOMER: " + ar.getCustomer().getEmail() + "\n" + "PRICE: " + ar.getPrice() + " RSD\n"
                    + "REPORTED: " + ar.isReported();
        else this.title = "NOT RESERVED";
    }
}
