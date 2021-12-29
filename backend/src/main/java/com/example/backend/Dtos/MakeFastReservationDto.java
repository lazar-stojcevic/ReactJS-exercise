package com.example.backend.Dtos;

import com.example.backend.Beans.AdditionalService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MakeFastReservationDto {
    private long instructorId;
    private long adventureId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime lastDateToReserve;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime adventureStart;
    private int length;
    private double discount;

    List<AdditionalService> addServices = new ArrayList<>();
}
