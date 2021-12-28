package com.example.backend.Beans;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "AdventureReservations")
public class AdventureReservation {
    @Id
    @SequenceGenerator(name = "myGen_adventureReservation", sequenceName = "mySeq_adventureReservation",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_adventureReservation")
    @Column(name = "adventure_reservation_id", unique = true, nullable = false)
    private long id;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime lastDateToReserve;

    @Column(nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime reservationStart;

    @Column(nullable = false)
    private int length;

    @Column(nullable = false)
    private int price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "adventure_id")
    private Adventure adventure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adventure_complaint_id")
    @JsonIgnore
    private AdventureComplaint complaint;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "add_services_of_adventure_reservation",
            joinColumns = @JoinColumn(name = "adventure_reservation_id", referencedColumnName = "adventure_reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "add_service_id", referencedColumnName = "add_service_id"))
    private List<AdditionalService> services = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "forbiden_to_reserve",
            joinColumns = @JoinColumn(name = "adventure_reservation_id"),
            inverseJoinColumns = @JoinColumn(name = "customer_id"))
    private List<Customer> forbidenCustomers = new ArrayList<>();

    @Column()
    private boolean rated = false;

    @Column()
    private boolean reported = false;

    public boolean isReserved(){
        return this.customer != null;
    }

    public void addAdditionalService(AdditionalService service){
        this.services.add(service);
    }
}
