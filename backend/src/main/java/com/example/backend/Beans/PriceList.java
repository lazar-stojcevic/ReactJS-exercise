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
@Table(name = "PriceLists")
public class PriceList {
    @Id
    @SequenceGenerator(name = "myGen_price_list", sequenceName = "mySeq_price_list",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_price_list")
    @Column(name = "price_lis_id", unique = true, nullable = false)
    private long id;

    @Column
    private int price;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "priceList", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<AdditionalServices> additionalServices = new HashSet<AdditionalServices>();

}
