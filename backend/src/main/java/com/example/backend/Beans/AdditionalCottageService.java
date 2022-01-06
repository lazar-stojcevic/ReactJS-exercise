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
@Table(name = "additional_cottage_services")
@Entity
public class AdditionalCottageService {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_add_cottage_services")
    @SequenceGenerator(name = "myGen_add_cottage_services", sequenceName = "mySeq_add_cottage_services")
    @Column(name = "add_cottage_service_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cottage_price_lis_id")
    private CottagePriceList priceList;

    @Column(length = 50)
    private String name;

    @Column
    private int addPrice;
}
