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
@Table(name = "holiday_timespan")
public class HolidayTimespan {
    @Id
    @SequenceGenerator(name = "myGen_holiday_timespan", sequenceName = "mySeq_holiday_timespan",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_holiday_timespan")
    @Column(name = "holiday_id", unique = true, nullable = false)
    private long id;

    private LocalDateTime fromDate;

    private LocalDateTime toDate;
}
