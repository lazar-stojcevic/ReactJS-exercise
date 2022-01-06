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
@Table(name = "CottagePriceLists")
public class CottagePriceList {
    @Id
    @SequenceGenerator(name = "myGen_cottage_price_list", sequenceName = "mySeq_cottage_price_list",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_cottage_price_list")
    @Column(name = "cottage_price_list_id", unique = true, nullable = false)
    private long id;

    @Column
    private int price;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "priceList", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AdditionalCottageService> additionalServices = new HashSet<>();

}
