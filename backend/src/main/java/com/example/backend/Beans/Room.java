package com.example.backend.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "Rooms")
public class Room {
    @Id
    @SequenceGenerator(name = "myGen_room", sequenceName = "mySeq_room",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_room")
    @Column(name = "room_id", unique = true, nullable = false)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cottage_id")
    @ToString.Exclude
    private Cottage cottage;

    @Column(length = 100)
    private String roomDescription;

    @Column
    private int numberOfBeds;


}
