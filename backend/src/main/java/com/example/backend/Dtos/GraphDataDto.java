package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class GraphDataDto {
    private LocalDate date;
    private int number = 0;

    public void incrementNumber(){
        number++;
    }
}
