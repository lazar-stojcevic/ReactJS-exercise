package com.example.backend.Beans;

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
@Table(name = "Adventures")
public class Adventure {
    @Id
    @SequenceGenerator(name = "myGen_adventure", sequenceName = "mySeq_adventure",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_adventure")
    @Column(name = "adventure_id", unique = true, nullable = false)
    private long id;

    @Column(length = 50)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "address_id")
    private Address address;

    @Column
    private int maxPersons;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String instructorBiography;

    @Column(length = 500)
    private String equipment;

    @Column()
    private String cancelingTerms;

    private String conductRules;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private FishingInstructor instructor;

    @OneToMany(mappedBy = "adventure", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Set<AdventureReservation> reservations = new HashSet<AdventureReservation>();

    @OneToOne(cascade = CascadeType.ALL)
    private PriceList priceList;
    //PRAVILA PONASANJA - u vidu teksta;
    //SLIKE
}
