package com.example.backend.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class AvailablePeriodDto {
    long id;
    long entityId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
}
