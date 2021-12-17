package com.example.backend.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    @Column
    private String cancelingTerms;

    @Column
    private String conductRules;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instructor_id")
    @ToString.Exclude
    private FishingInstructor instructor;

    @OneToMany(mappedBy = "adventure", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<AdventureReservation> reservations = new HashSet<AdventureReservation>();

    @OneToOne(cascade = CascadeType.ALL)
    private PriceList priceList;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "adventure", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Image> images = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "have_prepaid_on_adventures",
            joinColumns = @JoinColumn(name = "adventure_id", referencedColumnName = "adventure_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "user_id"))
    private List<Customer> prepaidCustomers = new ArrayList<>();
}
