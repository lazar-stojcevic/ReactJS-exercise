package com.example.backend.Beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "Tax")
public class Tax {
    @Id
    @SequenceGenerator(name = "myGen_tax", sequenceName = "mySeq_tax",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_tax")
    @Column(name = "adventure_id", unique = true, nullable = false)
    private long id;

    @Column
    private double taxRate;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    @Column
    private boolean current = true;
}
