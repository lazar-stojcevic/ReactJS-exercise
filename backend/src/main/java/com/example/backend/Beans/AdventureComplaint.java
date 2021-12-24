package com.example.backend.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "adventure_complaints")
public class AdventureComplaint {
    @Id
    @SequenceGenerator(name = "myGen_adventure_complaint", sequenceName = "mySeq_adventure_complaint",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_adventure_complaint")
    @Column(name = "adventure_complaint_id", unique = true, nullable = false)
    private long id;

    @Column(length = 5000)
    private String text;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adventure_reservation_id", nullable = false)
    private AdventureReservation reservation;

    @Column
    private boolean reviewed;


}
