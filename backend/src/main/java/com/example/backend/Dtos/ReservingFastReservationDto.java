package com.example.backend.Dtos;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.SequenceGenerator;

@Getter
@Setter
@NoArgsConstructor
public class ReservingFastReservationDto {
    private long userId;
    private long reservationId;
}
