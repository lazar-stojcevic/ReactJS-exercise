package com.example.backend.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "BoatImages")
public class BoatImage {
    @Id
    @SequenceGenerator(name = "myGen_imageB", sequenceName = "mySeq_imageB",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_imageB")
    @Column(name = "image_id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat_id")
    @ToString.Exclude
    private Boat boat;

    @Column
    private String base64;
}
