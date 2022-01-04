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
@Table(name = "BoatPriceLists")
public class BoatPriceList {
    @Id
    @SequenceGenerator(name = "myGen_boat_price_list", sequenceName = "mySeq_boat_price_list",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_boat_price_list")
    @Column(name = "boat_price_lis_id", unique = true, nullable = false)
    private long id;

    @Column
    private int price;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "priceList", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AdditionalBoatService> additionalServices = new HashSet<>();
}
