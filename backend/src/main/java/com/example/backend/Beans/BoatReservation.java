package com.example.backend.Beans;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "BoatReservations")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class BoatReservation {
    @Id
    @SequenceGenerator(name = "myGen_boatReservation", sequenceName = "mySeq_boatReservation",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_boatReservation")
    @Column(name = "boat_reservation_id", unique = true, nullable = false)
    private long id;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime reservationStart;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime reservationEnd;

    @Column(nullable = false)
    private int length;

    @Column()
    private boolean fast;

    @Column()
    private boolean captain;

    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "boat_id")
    private Boat boat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "complaint", referencedColumnName = "complaint_id")
    private Complaint complaint;

    @Column()
    private boolean rated = false;

    @Column()
    private double discount = 0;

    @Column()
    private boolean reported = false;

    @Version
    private Integer version;

    private double mark = 0;

    @OneToOne(cascade = CascadeType.ALL)
    private BoatPriceList boatPriceList;

    public boolean isReserved(){
        return this.customer != null;
    }

    public void addAdditionalService(AdditionalBoatService service){
        Set<AdditionalBoatService> additionalServices = this.boatPriceList.getAdditionalServices();
        additionalServices.add(service);
        this.boatPriceList.setAdditionalServices(additionalServices);
    }
}
