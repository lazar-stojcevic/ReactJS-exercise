package com.example.backend.Beans;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "available_timespan")
public class AvailableTimespan {
    @Id
    @SequenceGenerator(name = "myGen_available_timespan", sequenceName = "mySeq_available_timespan",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_available_timespan")
    @Column(name = "available_id", unique = true, nullable = false)
    private long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime fromDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime toDate;
}
