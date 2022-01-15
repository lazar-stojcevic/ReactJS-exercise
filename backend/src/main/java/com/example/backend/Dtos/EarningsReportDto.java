package com.example.backend.Dtos;

import com.example.backend.Beans.CottageReservation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class EarningsReportDto {
    public double price;
    public Collection<CottageReservation> reservationList;
}
