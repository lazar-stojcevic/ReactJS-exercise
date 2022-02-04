package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CustomerReserveTermDto {
    private long reservationId;
    private long userId;
    private List<Long> services;
}
