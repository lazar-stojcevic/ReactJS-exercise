package com.example.backend.Beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "additional_boat_services")
@Entity
public class AdditionalBoatService {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_add_boat_services")
    @SequenceGenerator(name = "myGen_add_boat_services", sequenceName = "mySeq_add_boat_services")
    @Column(name = "add_boat_service_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "boat_price_list_boat_price_lis_id")
    private BoatPriceList priceList;

    @Column(length = 50)
    private String name;

    @Column
    private int addPrice;
}
