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
    private LocalDateTime startDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime endDate;
    private int price;
    private String title;
    private boolean reported;

    public CalendarReservationsDto(AdventureReservation ar){
        this.id = ar.getId();
        this.startDate = ar.getReservationStart();
        this.endDate = ar.getReservationStart().plusHours(ar.getLength());
        this.price = ar.getPrice();
        this.reported = ar.isReported();
        if(ar.getCustomer() != null)
            this.title = ar.getCustomer().getEmail();
        else this.title = "NOT RESERVED";
    }
}
