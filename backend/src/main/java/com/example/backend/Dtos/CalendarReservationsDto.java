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
    private LocalDateTime start_date;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime end_date;
    private int price;
    private String text;
    private boolean reported;

    public CalendarReservationsDto(AdventureReservation ar){
        this.id = ar.getId();
        this.start_date = ar.getReservationStart();
        this.end_date = ar.getReservationStart().plusHours(ar.getLength());
        this.price = ar.getPrice();
        this.reported = ar.isReported();
        if(ar.getCustomer() != null)
            this.text = ar.getCustomer().getEmail();
        else this.text = "NOT RESERVED";
    }
}
