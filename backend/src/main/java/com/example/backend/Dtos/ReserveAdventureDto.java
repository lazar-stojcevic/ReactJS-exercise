package com.example.backend.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReserveAdventureDto {
    private List<Long> selectedAdditionalServicesIds;
    private String customerMail;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime reservationStart;
    private int length;
    private long adventureId;
}
