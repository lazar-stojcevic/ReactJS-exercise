package com.example.backend.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "additional_services")
@Entity
public class AdditionalServices {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_add_services")
    @SequenceGenerator(name = "myGen_add_services", sequenceName = "mySeq_add_services")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "price_list_price_lis_id")
    private PriceList priceList;

    @Column(length = 50)
    private String name;

    @Column
    private int addPrice;
}