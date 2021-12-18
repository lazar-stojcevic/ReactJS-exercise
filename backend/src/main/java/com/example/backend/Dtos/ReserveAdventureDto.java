package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReserveAdventureDto {
    long customerId;
    long adventureReservationId;
    List<Long> selectedAdditionalServicesIds;
}
