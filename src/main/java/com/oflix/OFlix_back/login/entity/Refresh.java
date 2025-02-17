package com.oflix.OFlix_back.login.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "refresh")
public class Refresh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "user_id", nullable = false)
    private String username;

    @Column(name = "refresh", nullable = false)
    private String refresh;

    @Column(name = "expiration", nullable = false)
    private String expiration;
}
