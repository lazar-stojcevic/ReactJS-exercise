package com.example.backend.Beans;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class HolidayTimespan {
    @Id
    @SequenceGenerator(name = "myGen_holiday", sequenceName = "mySeq_holiday",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_holiday")
    @Column(name = "holiday_id", unique = true, nullable = false)
    private long id;
    @Column
    private LocalDateTime from;
    @Column
    private LocalDateTime to;
}
