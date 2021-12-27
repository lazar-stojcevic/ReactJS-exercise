package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerOnRequestForDeletingDto {
    private String answer;
    private boolean forDelete;
    private long userId;
}
