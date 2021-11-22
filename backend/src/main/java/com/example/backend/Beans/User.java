package com.example.backend.Beans;

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
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User {
    @Id
    @SequenceGenerator(name = "myGen", sequenceName = "mySeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen")
    @Column(name = "user_id", unique = true, nullable = false)
    private long id;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String lastname;

    @Column(unique = true, nullable = false, updatable = false, length = 100)
    private String eMail;

    @Column(nullable = false, length = 30)
    private String password;

    @Column(length = 50)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "address_id")
    private Address address;
}
