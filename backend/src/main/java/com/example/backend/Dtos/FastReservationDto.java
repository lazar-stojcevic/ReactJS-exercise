package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class FastReservationDto {
    private long id;
    private long cottageId;
    private LocalDateTime date1;
    private LocalDateTime date2;
    private String description;
    private int sale;
}
