package com.example.backend.Beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Complaint {
    @Id
    @SequenceGenerator(name = "myGen_complaint1", sequenceName = "mySeq_complaint1",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_complaint1")
    @Column(name = "complaint_id", unique = true, nullable = false)
    private long id;

    @Column(length = 5000)
    private String text;

    @Column
    private boolean reviewed;
}
