package com.example.backend.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "CottageReservations")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CottageReservation {
    @Id
    @SequenceGenerator(name = "myGen_cottageReservation", sequenceName = "mySeq_cottageReservation",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_cottageReservation")
    @Column(name = "cottage_reservation_id", unique = true, nullable = false)
    private long id;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime reservationStart;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime reservationEnd;

    @Column(nullable = false)
    private int length;

    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cottage_id")
    private Cottage cottage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column()
    private boolean rated = false;

    @Column()
    private double discount = 0;

    private double mark = 0;

    @OneToOne(cascade = CascadeType.ALL)
    private CottagePriceList CottagePriceList;

    public boolean isReserved(){
        return this.customer != null;
    }

    public void addAdditionalService(AdditionalCottageService service){
        Set<AdditionalCottageService> additionalServices = this.CottagePriceList.getAdditionalServices();
        additionalServices.add(service);
        this.CottagePriceList.setAdditionalServices(additionalServices);
    }
}
