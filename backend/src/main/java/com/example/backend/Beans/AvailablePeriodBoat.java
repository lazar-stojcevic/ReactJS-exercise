package com.example.backend.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name = "AvailablePeriodBoats")
public class AvailablePeriodBoat {
    @Id
    @SequenceGenerator(name = "myGen_available_period_boat", sequenceName = "mySeq_available_period_boat",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_available_period_boat")
    @Column(name = "available_id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat_id")
    @ToString.Exclude
    private Boat boat;

    @Column()
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime fromDate;

    @Column()
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime toDate;
}
