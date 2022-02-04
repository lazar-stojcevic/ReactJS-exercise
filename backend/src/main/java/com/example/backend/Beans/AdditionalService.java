package com.example.backend.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString
@Table(name = "additional_services")
@Entity
public class AdditionalService {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_add_services")
    @SequenceGenerator(name = "myGen_add_services", sequenceName = "mySeq_add_services")
    @Column(name = "add_service_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "price_list_price_list_id")
    private PriceList priceList;

    @Column(length = 50)
    private String name;

    @Column
    private int addPrice;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "services")
    @JsonIgnore
    private List<AdventureReservation> adventureReservations = new ArrayList<>();
}