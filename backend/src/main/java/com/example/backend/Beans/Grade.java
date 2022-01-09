package com.example.backend.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Grades")
public class Grade {
    @Id
    @SequenceGenerator(name = "myGen_grade", sequenceName = "mySeq_grade",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_grade")
    @Column(name = "grade_id", unique = true, nullable = false)
    private long id;

    @Column
    private int rating;

    @Column(length = 5000)
    private String revision;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    @ToString.Exclude
    private FishingInstructor instructor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cottage_id")
    @ToString.Exclude
    private Cottage cottage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat_id")
    @ToString.Exclude
    private Boat boat;

    private boolean enabled = false;
}
