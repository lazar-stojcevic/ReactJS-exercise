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
@Table(name = "Cottages")
public class Cottage {

    @Id
    @SequenceGenerator(name = "myGen_cottage", sequenceName = "mySeq_cottage",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_cottage")
    @Column(name = "cottage_id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private CottageOwner cottageOwner;

    @Column(length = 50)
    private String name;

    @Column
    private String conductRules;

    @Column
    private String promo;

    @Column
    private double rating;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "address_id")
    private Address address;

    @OneToMany(mappedBy = "cottage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<CottageImage> images = new HashSet<CottageImage>();

    @OneToMany(mappedBy = "cottage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private Set<Room> rooms = new HashSet<Room>();

    @OneToOne(cascade = CascadeType.ALL)
    private PriceList priceList;

}
