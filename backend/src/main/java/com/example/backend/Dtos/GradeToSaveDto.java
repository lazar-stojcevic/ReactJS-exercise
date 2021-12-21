package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GradeToSaveDto {
    private int rating;
    private String revision;
    private long entityId;
    //I-instructor, BO-boatOwner, CO-cottageOwner, C-cottage, B-boat
    private String entityName;
}
