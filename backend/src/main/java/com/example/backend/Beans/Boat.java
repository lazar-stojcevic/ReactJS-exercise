package com.example.backend.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Boats")
public class Boat {
    @Id
    @SequenceGenerator(name = "myGen_boat", sequenceName = "mySeq_boat",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_boat")
    @Column(name = "boat_id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private BoatOwner boatOwner;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String type;

    @Column()
    private int numberOfEngines;

    @Column()
    private double enginePower;

    @Column()
    private double topSpeed;

    @Column()
    private String promo;

    @Column
    private String conductRules;

    @Column()
    private int capacity;

    @OneToMany(mappedBy = "boat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<BoatImage> images = new HashSet<>();


    @OneToOne(cascade = CascadeType.ALL)
    private BoatPriceList priceList;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "address_id")
    private Address address;
}
