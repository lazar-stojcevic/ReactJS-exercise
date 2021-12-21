package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class GradeToShowDto {
    private double avgRating;
    private List<String> comments = new ArrayList<>();
}
