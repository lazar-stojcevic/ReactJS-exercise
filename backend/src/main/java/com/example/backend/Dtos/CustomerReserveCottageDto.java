package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerReserveCottageDto {
    private long cottageId;
    private long userId;
    private LocalDateTime from;
    private LocalDateTime to;
    private List<Long> services;
}
