package com.example.backend.Beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


@Entity
@Getter
@Setter
@ToString
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="user_type", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements UserDetails {
    @Id
    @SequenceGenerator(name = "myGen", sequenceName = "mySeq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen")
    @Column(name = "user_id", unique = true, nullable = false)
    private long id;

    @Column(length = 50)
    private String firstname;

    @Column(length = 50)
    private String lastName;

    @Column(unique = true, updatable = false, length = 100)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 50)
    private String phone;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @Column (name = "enabled")
    private boolean enabled = false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address", referencedColumnName = "address_id")
    private Address address;

    public boolean getEnabled(){
        return this.enabled;
    }


}
