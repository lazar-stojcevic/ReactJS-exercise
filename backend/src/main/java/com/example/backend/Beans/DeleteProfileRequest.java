package com.example.backend.Beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "delete_profile_requests")
public class DeleteProfileRequest {
    @Id
    @SequenceGenerator(name = "myGen_request", sequenceName = "mySeq_request",
            initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myGen_request")
    @Column(name = "delete_profile_request_id", unique = true, nullable = false)
    private long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String RequestText;

    @Column
    private boolean reviewed;


}
