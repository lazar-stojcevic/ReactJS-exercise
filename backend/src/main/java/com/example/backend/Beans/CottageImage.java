package com.example.backend.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Images")
public class CottageImage {
    @Id
    @SequenceGenerator(name = "myGen_image", sequenceName = "mySeq_image",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_image")
    @Column(name = "image_id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cottage_id")
    @ToString.Exclude
    private Cottage cottage;

    @Column(length = 400000)
    private String base64;
}
