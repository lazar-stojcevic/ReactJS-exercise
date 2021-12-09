package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@NoArgsConstructor
public class RoomDto {
    private long id;
    private long cottageId;
    private String roomDescription;
    private int numberOfBeds;
}
