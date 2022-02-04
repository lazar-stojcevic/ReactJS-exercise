package com.example.backend.Dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MakeReportDto {
    private String comment;
    private boolean customerAppear;
    private boolean badReport;
    private long reservationId;
    //AR, BR, CR
    private String reservationType;
}
