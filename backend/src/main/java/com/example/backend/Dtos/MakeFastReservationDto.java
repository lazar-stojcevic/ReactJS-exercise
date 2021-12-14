package com.example.backend.Dtos;

import com.example.backend.Beans.AdventureReservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MakeFastReservationDto {
    private AdventureReservation adventureReservation;
    private long instructorId;
    private long adventureId;
}
