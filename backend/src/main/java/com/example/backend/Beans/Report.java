package com.example.backend.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "reports")
public class Report {
    @Id
    @SequenceGenerator(name = "myGen_report", sequenceName = "mySeq_report",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_report")
    @Column(name = "report_id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(length = 3000)
    private String comment;

    @Column()
    private boolean processed = false;

    @Column()
    private boolean customerAppear = true;

    @Column()
    private boolean badReport = false;
}
