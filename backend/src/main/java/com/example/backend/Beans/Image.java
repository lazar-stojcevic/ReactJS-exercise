package com.example.backend.Beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Image {
    @Id
    @SequenceGenerator(name = "myGen_img", sequenceName = "mySeq_img", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_img")
    @Column(name = "user_id", unique = true, nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "adventure_id")
    private Adventure adventure;

    @Column(length = 300000)
    private String image;

}
